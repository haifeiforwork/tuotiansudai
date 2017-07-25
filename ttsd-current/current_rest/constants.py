# coding=utf-8
LOAN_STATUS_APPROVING = 'APPROVING'
LOAN_STATUS_APPROVED = 'APPROVED'
LOAN_STATUS_EXPIRED = 'EXPIRED'

LOAN_STATUS_CHOICES = (
    (LOAN_STATUS_APPROVING, u'待审核'),
    (LOAN_STATUS_APPROVED, u'已审核'),
    (LOAN_STATUS_EXPIRED, u'已过期')
)

LOAN_TYPE_HOUSE = 'HOUSE'
LOAN_TYPE_VEHICLE = 'VEHICLE'
LOAN_TYPE_FACTORING = 'FACTORING'
LOAN_TYPE_BILL = 'BILL'
LOAN_TYPE_HUIZU = 'HUIZU'
LOAN_TYPE_SHUIYI = 'SHUIYI'

LOAN_TYPE_CHOICES = (
    (LOAN_TYPE_HOUSE, u'房产抵押借款'),
    (LOAN_TYPE_VEHICLE, u'车辆抵押借款'),
    (LOAN_TYPE_FACTORING, u'企业经营性借款-保理'),
    (LOAN_TYPE_BILL, u'企业经营性借款-票据'),
    (LOAN_TYPE_SHUIYI, u'税易经营性借款'),
    (LOAN_TYPE_HUIZU, u'汽车融资租赁')
)


class OperationTarget(object):
    LOAN = 'loan'

    OPERATION_TARGET_TYPE = (
        (LOAN, u'债权'),
    )


class OperationType(object):
    LOAN_ADD = 'LOAN_ADD'
    LOAN_AUDIT = 'LOAN_AUDIT'
    OPERATION_TYPE_MAP = (
        (LOAN_ADD, u'增加债权信息'),
        (LOAN_AUDIT, u'审核债权信息'),
    )

SOURCE_WEB = 'WEB'
SOURCE_WE_CHAT = 'WE_CHAT'
SOURCE_IOS = 'IOS'
SOURCE_ANDROID = 'ANDROID'
SOURCE_CHOICE = (
    (SOURCE_WEB, u'网站'),
    (SOURCE_WE_CHAT, u'微信'),
    (SOURCE_IOS, u'iOS'),
    (SOURCE_ANDROID, u'Android')
)

DEPOSIT_WAITING_PAY = 'WAITING_PAY'
DEPOSIT_SUCCESS = 'SUCCESS'
DEPOSIT_FAIL = 'FAIL'
DEPOSIT_STATUS_CHOICE = (
    (DEPOSIT_WAITING_PAY, u'待支付'),
    (DEPOSIT_SUCCESS, u'买入成功'),
    (DEPOSIT_FAIL, u'买入失败'),
)

BILL_TYPE_DEPOSIT = 'DEPOSIT'
BILL_TYPE_WITHDRAW = 'WITHDRAW'
BILL_TYPE_INTEREST = 'INTEREST'
BILL_TYPE_CHOICE = (
    (BILL_TYPE_DEPOSIT, u'买入'),
    (BILL_TYPE_WITHDRAW, u'赎回'),
    (BILL_TYPE_INTEREST, u'结息'),
)

REDEEM_DOING = 'DOING'
REDEEM_SUCCESS = 'SUCCESS'
REDEEM_FAIL = 'FAIL'
REDEEM_STATUS_CHOICE = (
    (REDEEM_DOING, u'赎回中'),
    (REDEEM_SUCCESS, u'赎回成功'),
    (REDEEM_FAIL, u'赎回失败'),
)
