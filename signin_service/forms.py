import wtforms
from werkzeug.routing import ValidationError

import constants
from models import User


class LoginForm(wtforms.Form):
    username = wtforms.StringField('username',
                                   [wtforms.validators.required(), wtforms.validators.Length(min=5, max=25)])
    password = wtforms.StringField('password',
                                   [wtforms.validators.required(), wtforms.validators.Length(min=6, max=20)])

    token = wtforms.StringField('token', [wtforms.validators.required()])
    source = wtforms.StringField('source')
    device_id = wtforms.StringField('device_id')


class LoginAfterRegisterForm(wtforms.Form):
    username = wtforms.StringField('username',
                                   [wtforms.validators.required(), wtforms.validators.Length(min=5, max=25)])
    token = wtforms.StringField('token', [wtforms.validators.required()])
    source = wtforms.StringField('source')
    device_id = wtforms.StringField('device_id')


class SourceForm(wtforms.Form):
    source = wtforms.StringField('source', [wtforms.validators.required()])


class RefreshTokenForm(SourceForm):
    def validate_source(self, field):
        if field.data.upper() not in ('IOS', 'ANDROID'):
            raise ValidationError('Source must be either IOS or ANDROID')


class UserRegisterForm(wtforms.Form):
    mobile = wtforms.StringField('mobile', [wtforms.validators.required(), wtforms.validators.Regexp(r'^1\d{10}$')])
    password = wtforms.StringField('password', [wtforms.validators.required(),
                                                wtforms.validators.Regexp(r'^(?=.*[^\d])(.{6,20})$')])
    referrer = wtforms.StringField('referrer', filters=[lambda x: x or None])
    channel = wtforms.StringField('channel', [wtforms.validators.Length(max=32)])
    source = wtforms.StringField('source', [wtforms.validators.required()])

    def validate_mobile(self, field):
        if User.query.filter((User.mobile == field.data)).first():
            raise ValidationError('user {} already exists'.format(field.data))

    def validate_referrer(self, field):
        if field.data and not User.query.filter((User.login_name == field.data)).first():
            raise ValidationError('referrer {} was not exist'.format(field.data))

    def validate_source(self, field):
        if field.data not in constants.USER_SOURCES:
            raise ValidationError('source must be a value in {}'.format(constants.USER_SOURCES))


class UserUpdateForm(wtforms.Form):
    login_name = wtforms.StringField('login_name',
                                     [wtforms.validators.required(), wtforms.validators.Length(min=5, max=25)])
    mobile = wtforms.StringField('mobile', [wtforms.validators.Regexp(r'^(1\d{10})?$')])
    email = wtforms.StringField('email')
    user_name = wtforms.StringField('user_name', [wtforms.validators.Length(max=50)])
    identity_number = wtforms.StringField('identity_number', [wtforms.validators.Length(max=18)])
    last_modified_time = wtforms.DateTimeField('last_modified_time')
    last_modified_user = wtforms.StringField('last_modified_user', [wtforms.validators.Length(max=25)])
    avatar = wtforms.StringField('avatar', [wtforms.validators.Length(max=256)])
    referrer = wtforms.StringField('referrer', [wtforms.validators.Length(max=25)])
    status = wtforms.StringField('status', [wtforms.validators.Length(max=20)])
    channel = wtforms.StringField('channel', [wtforms.validators.Length(max=32)])
    province = wtforms.StringField('province', [wtforms.validators.Length(max=32)])
    city = wtforms.StringField('city', [wtforms.validators.Length(max=32)])
    source = wtforms.StringField('source', [wtforms.validators.Length(max=16)])

    def validate_login_name(self, field):
        if field.data and not User.query.filter(User.login_name == field.data).first():
            raise ValidationError('user {} not exist'.format(field.data))

    def validate_mobile(self, field):
        if field.data and User.query.filter(
                        (User.mobile == field.data) & (User.login_name != self.login_name.data)).first():
            raise ValidationError('user {} already exists'.format(field.data))

    def validate_referrer(self, field):
        if field.data and not User.query.filter((User.login_name == field.data)).first():
            raise ValidationError('referrer {} not exist'.format(field.data))

    def validate_status(self, field):
        if field.data and field.data not in constants.USER_STATUSES:
            raise ValidationError('status must be a value in {}'.format(constants.USER_STATUSES))


class UserResetPasswordForm(wtforms.Form):
    login_name = wtforms.StringField('login_name',
                                     [wtforms.validators.required(), wtforms.validators.Length(min=5, max=25)])
    password = wtforms.StringField('password', [wtforms.validators.required(),
                                                wtforms.validators.Regexp(r'^(?=.*[^\d])(.{6,20})$')])

    def validate_login_name(self, field):
        if field.data and not User.query.filter(User.login_name == field.data).first():
            raise ValidationError('user {} not exist'.format(field.data))


class UserChangePasswordForm(UserResetPasswordForm):
    ori_password = wtforms.StringField('ori_password',
                                       [wtforms.validators.required(), wtforms.validators.Length(min=6, max=20)])


class UserExperienceAccountForm(wtforms.Form):
    login_name = wtforms.StringField('login_name',
                                     [wtforms.validators.required(), wtforms.validators.Length(min=5, max=25)])
    amount = wtforms.IntegerField('amount', [wtforms.validators.required()])
