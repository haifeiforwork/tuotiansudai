require(['jquery', 'layerWrapper', 'template', 'commonFun', 'jquery.validate', 'jquery.validate.extension', 'jquery.ajax.extension', 'jquery.form'], function($, layer, tpl) {
    $(function() {
        var $registerForm = $('#registerForm'),
            $loginForm = $('#loginForm'),
            $attestForm = $('#attestForm'),
            mobileValid = false,
            passwordValid = false,
            captchaValid = false,
            $appCaptcha = $('#appCaptcha', $registerForm),
            $fetchCaptcha = $('.fetch-captcha', $registerForm);

        var lottery = {
            click: false, //防止重复点击
            index: -1, //当前转动到哪个位置，起点位置
            count: 0, //总共有多少个位置
            timer: 0, //setTimeout的ID，用clearTimeout清除
            speed: 20, //初始转动速度
            times: 0, //转动次数
            cycle: 50, //转动基本次数：即至少需要转动多少次再进入抽奖环节
            prize: -1, //中奖位置
            num:0,
            init: function(id) {
                if ($("#" + id).find(".lottery-unit").length > 0) {
                    $lottery = $("#" + id);
                    $units = $lottery.find(".lottery-unit");
                    this.obj = $lottery;
                    this.count = $units.length;
                    $lottery.find(".lottery-unit-" + this.index).addClass("active");
                };
            },
            roll: function() {
                var index = this.index,
                    count = this.count,
                    lottery = this.obj;
                $(lottery).find(".lottery-unit-" + index).removeClass("active");
                index += 1;
                if (index > count - 1) {
                    index = 0;
                };
                $(lottery).find(".lottery-unit-" + index).addClass("active");
                this.index = index;
                return false;
            },
            show:function(num){
                $('#lottery').find(".lottery-unit-"+num).addClass("active").removeClass('hiding').siblings().removeClass('active');
                // lottery.prize=num;
            },
            eventRoll: function() {
                lottery.times += 1;
                lottery.roll(); //转动过程调用的是lottery的roll方法，这里是第一次调用初始化
                if (lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
                    clearTimeout(lottery.timer);
                    lottery.prize = -1;
                    lottery.times = 0;
                    lottery.click = false;
                    selectTip();
                    
                } else {
                    if (lottery.times < lottery.cycle) {
                        lottery.speed -= 10;
                    } else if (lottery.times == lottery.cycle) {
                        var index = Math.random()*(lottery.count)|0;
                        lottery.prize = index; 
                    } else {
                        if (lottery.times > lottery.cycle + 10 && ((lottery.prize == 0 && lottery.index == 7) || lottery.prize == lottery.index + 1)) {
                            lottery.speed += 110;
                        } else {
                            lottery.speed += 20;
                        }
                    }
                    if (lottery.speed < 40) {
                        lottery.speed = 40;
                    };
                    lottery.timer = setTimeout(lottery.eventRoll, lottery.speed); //循环调用
                }
                return false;
            }
        };



        lottery.init('lottery');
        $("#lottery .lottery-btn").on('click', function(event) {
            event.preventDefault();

            if (lottery.click) {
                return false;
            } else {
                $("#lottery .lottery-unit").addClass('hiding');
                lottery.speed = 100;
                lottery.eventRoll();
                lottery.click = true;
            }
        });
        //判断是第几次抽奖
        function selectTip(){
            if($('#loginMobile').val()!=''){
                lastTip();
            }else{
                registerTip();
            }
        }
        //发送获奖请求
        function getGift(type) {
            
            $.ajax({
                    url: '/activity/point-draw/task-draw',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        mobile: $('#loginMobile').val()
                    }
                })
                .done(function(data) {
                    if (data.returnCode == 1) {//无抽奖机会
                        noChanceTip();
                    } else if (data.returnCode == 0) {//获奖数据
                        switch (data.prize) {
                            case 'M1_PHONE': //锤子M1手机
                                lottery.num = 7;
                                lottery.text='锤子M1手机';
                                break;
                            case 'HUMIDIFIER': //小熊加湿器
                                lottery.num = 0;
                                lottery.text='小熊加湿器';
                                break;
                            case 'HAIR_DRIER': //飞科电吹风机
                                lottery.num = 1;
                                lottery.text='飞科电吹风机';
                                break;
                            case 'IQIYI_MEMBERSHIP_REF_CARNIVAL': //爱奇艺会员
                                lottery.num = 5;
                                lottery.text='爱奇艺会员';
                                break;
                            case 'TELEPHONE_FARE_10_REF_CARNIVAL': //10元话费
                                lottery.num = 2;
                                lottery.text='10元话费';
                                break;
                            case 'BAMBOO_CHARCOAL_PACKAGE': //卡通汽车竹炭包
                                lottery.num = 6;
                                lottery.text='卡通汽车竹炭包';
                                break;
                            case 'INTEREST_COUPON_5_POINT_DRAW_REF_CARNIVAL': //0.5加息券
                                lottery.num = 3;
                                lottery.text='0.5加息券';
                                break;
                            case 'RED_ENVELOPE_50_POINT_DRAW_REF_CARNIVAL': //50元红包
                                lottery.num = 4;
                                lottery.text='50元红包';
                                break;
                        }
                        lottery.show(lottery.num);
                        if(type=='register'){
                            $('#attestBox').find('.gift-name').text('注册成功，获得了'+lottery.text);
                        }else if(type=='user'){
                            $('#lastBox').find('.gift-name').text('恭喜您解救了'+lottery.text);
                        }
                    } else if (data.returnCode == 3) {//活动结束
                        layer.msg('当前活动已结束！');
                    }
                })
                .fail(function() {
                    layer.msg('抽奖失败，请重试');
                });
        }
        //点击页面底部下载链接
        $('.download-btn').on('click', function(event) {
            event.preventDefault();
            location.href = $(this).attr('data-href');
        });
        //刷新注册验证码
        function refreshCapt() {
            $('.captcha-register').attr('src', '/register/user/image-captcha?' + new Date().getTime().toString());
        }
        refreshCapt();
        //换一张注册验证码
        $('.captcha-register').on('click', refreshCapt);

        //图形验证码输入后高亮显示获取验证码
        $appCaptcha.on('keyup', function(event) {
            if (/^\d{5}$/.test(event.target.value)) {
                $(event.target).addClass('valid').removeClass('error');
                captchaValid = true;
                mobileValid = $('#mobile').hasClass('valid');
                passwordValid = $('#password').hasClass('valid');
                if (mobileValid && passwordValid && captchaValid) {
                    $fetchCaptcha.prop('disabled', false);
                    $('#appCaptcha-error').html('').hide();
                } else {
                    $fetchCaptcha.prop('disabled', true);
                }

            }
        });

        // 获取手机验证码
        $fetchCaptcha.on('click', function(event) {
            var $this = $(this);
            event.preventDefault();
            if ($this.prop('disabled')) {
                return;
            }
            $fetchCaptcha.prop('disabled', true);
            var captchaVal = $appCaptcha.val(),
                mobile = $('#mobile').val();
            $.ajax({
                    url: '/register/user/send-register-captcha',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        imageCaptcha: captchaVal,
                        mobile: mobile
                    }
                })
                .done(function(response) {
                    var data = response.data,
                        countdown = 60,
                        timer;
                    if (data.status && !data.isRestricted) {
                        timer = setInterval(function() {
                            $fetchCaptcha.prop('disabled', true).text(countdown + '秒后重发');
                            countdown--;
                            if (countdown == 0) {
                                clearInterval(timer);
                                $fetchCaptcha.prop('disabled', false).text('重新发送');
                            }
                        }, 1000);
                        return;
                    }
                    if (!data.status && data.isRestricted) {
                        layer.msg('短信发送频繁,请稍后再试');
                    }

                    if (!data.status && !data.isRestricted) {
                        layer.msg('图形验证码错误');
                        refreshCapt();
                    }
                })
                .fail(function() {
                    layer.msg('请求失败，请重试！');
                    $fetchCaptcha.prop('disabled', false);
                    refreshCapt();
                });
        });
        //协议弹框
        $('.show-agreement').on('click', function(event) {
            event.preventDefault();
            layer.open({
                type: 1,
                title: '拓天速贷服务协议',
                area: ['100%', '100%'],
                shadeClose: true,
                move: false,
                scrollbar: true,
                skin: 'register-skin',
                content: $('#agreementBox')
            });
        });
        // 电话号码是否有效
        jQuery.validator.addMethod("isPhone", function(value, element) {
            var tel = /0?(13|14|15|18)[0-9]{9}/;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的手机号码");

        //验证码是否正确
        jQuery.validator.addMethod("checkCaptcha", function(value, element) {
            var mobile = $('#mobile').val();
            var deferred = $.Deferred();
            if (/^\d{6}$/.test(value) && mobile) {
                $.ajax({
                    url: '/register/user/mobile/' + mobile + '/captcha/' + value + '/verify',
                    async: false,
                    dataType: "json",
                    success: function(response) {
                        var status = response.data.status;
                        if (status) {
                            deferred.resolve();
                            captchaValid = true;
                        } else {
                            deferred.reject();
                            captchaValid = false;
                        }
                    }
                });
            } else {
                deferred.reject();
                captchaValid = false;
            }
            return deferred.state() == "resolved" ? true : false;
        }, "验证码不正确");
        //注册校验
        $registerForm.validate({
            debug: true,
            rules: {
                mobile: {
                    required: true,
                    digits: true,
                    isPhone: true,
                    minlength: 11,
                    maxlength: 11,
                    isExist: "/register/user/mobile/{0}/is-exist"
                },
                password: {
                    required: true,
                    regex: /^(?=.*[^\d])(.{6,20})$/
                },
                appCaptcha: {
                    required: true
                },
                captcha: {
                    required: true,
                    digits: true,
                    maxlength: 6,
                    minlength: 6,
                    checkCaptcha: true
                }
            },
            messages: {
                mobile: {
                    required: '请输入手机号',
                    digits: '必须是数字',
                    minlength: '手机格式不正确',
                    isPhone: '请输入正确的手机号码',
                    maxlength: '手机格式不正确',
                    isExist: '手机号已存在'
                },
                password: {
                    required: "请输入密码",
                    regex: '6位至20位，不能全是数字'
                },
                appCaptcha: {
                    required: '请输入验证码'
                },
                captcha: {
                    required: '请输入手机验证码',
                    digits: '验证码格式不正确',
                    maxlength: '验证码格式不正确',
                    minlength: '验证码格式不正确',
                    checkCaptcha: '验证码不正确'
                }
            },
            submitHandler: function(form) {
                $.ajax({
                    url: '/register/user',
                    data: $(form).serialize(),
                    type: 'POST',
                    dataType: 'json',
                    beforeSend: function() {
                        $registerForm.find('.register-user').prop('disabled', true);
                    }
                }).done(function(data) {
                    $('#loginMobile').val($('#mobile').val());
                    attestTip();
                }).fail(function() {
                    layer.msg('请求失败，请重试！');
                });
            }
        });
        //注册弹框显示
        function registerTip() {
            layer.closeAll();
            layer.open({
                type: 1,
                title: false,
                closeBtn: 2,
                content: $('#registerBox')
            });
            refreshCapt();
        }
        //登录弹框显示
        function loginTip() {
            layer.closeAll();
            layer.open({
                type: 1,
                title: false,
                closeBtn: 2,
                content: $('#loginBox')
            });
            refreshLogin();
        }
        //身份认证
        function attestTip() {
            layer.closeAll();
            getGift('register');
            layer.open({
                type: 1,
                title: false,
                closeBtn: 2,
                content: $('#attestBox')
            });
        }
        //下载弹框
        function lastTip() {
            layer.closeAll();
            getGift('user');
            layer.open({
                type: 1,
                title: false,
                closeBtn: 2,
                content: $('#lastBox')
            });
        }
        //没机会弹框
        function noChanceTip() {
            layer.closeAll();
            layer.open({
                type: 1,
                title: false,
                closeBtn: 2,
                content: $('#noChanceBox')
            });
        }
        //去登录按钮事件
        $('.login-btn', $('#registerBox')).on('click', loginTip);
        //去注册按钮事件
        $('.go-register', $('#loginBox')).on('click', registerTip);
        //刷新登录验证码
        function refreshLogin() {
            $('.captcha-login').attr('src', '/login/captcha?' + new Date().getTime().toString());
        }
        refreshLogin();
        //换一张登录验证码
        $('.captcha-login').on('click', refreshLogin);
        //登录表单校验
        $loginForm.validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                captcha: {
                    required: true,
                    minlength: 5
                }
            },
            messages: {
                username: {
                    required: '用户名不能为空'
                },
                password: {
                    required: '密码不能为空'
                },
                captcha: {
                    required: '验证码不能为空',
                    minlength: '验证码不正确'
                }
            },
            submitHandler: function(form) {
                $.ajax({
                    url: '/login',
                    data: $(form).serialize(),
                    type: 'POST',
                    dataType: 'json',
                    beforeSend: function() {
                        $loginForm.find('.register-user').prop('disabled', true);
                    }
                }).done(function(data) {
                    $('#loginMobile').val($('#mobile').val());
                    $('#lastBox').find('.gift-title').hide();
                    attestTip();
                }).fail(function() {
                    layer.msg('请求失败，请重试！');
                });
            }
        });

        $attestForm.validate({
            focusCleanup: true,
            focusInvalid: false,
            onfocusout: function(element) {
                this.element(element);
                if ($('input.valid', $attestForm).length == 2) {
                    $attestForm.find('.register-user').prop('disabled', false);
                }
            },
            submitHandler: function(form) {
                $(form).ajaxSubmit({
                    dataType: 'json',
                    beforeSubmit: function(arr, $form, options) {
                        $attestForm.find('.register-user').val('认证中...').prop('disabled', true);
                    },
                    success: function(response) {
                        if (response.data.status) {
                            $attestForm.find('.register-user').val('立即认证');
                            layer.closeAll();
                            layer.msg('认证成功');
                            setInterval(lastTip, 3000);
                        } else {
                            layer.msg('认证失败，请检查后重试！');
                            $attestForm.find('.register-user').val('立即认证').prop('disabled', false);
                        }

                    },
                    error: function(errorMap) {
                        layer.msg('认证失败，请检查后重试！');
                        $attestForm.find('.register-user').val('立即认证').prop('disabled', false);
                    }
                });
                return false;
            },
            onkeyup: function(element, event) {

                var excludedKeys = [16, 17, 18, 20, 35, 36, 37, 38, 39, 40, 45, 144, 225];

                if ((event.which !== 9 || this.elementValue(element) !== "") && $.inArray(event.keyCode, excludedKeys) === -1) {
                    this.element(element);
                }
            },
            rules: {
                username: {
                    required: true
                },
                identityNumber: {
                    required: true,
                    identityCheckValid: true,
                    identityCardAge: true,
                    isExist: "/register/account/identity-number/{0}/is-exist"
                }
            },
            messages: {
                username: {
                    required: "请输入姓名"
                },
                identityNumber: {
                    required: '请输入身份证号',
                    identityCheckValid: '您的身份证号码不正确',
                    identityCardAge: '年龄未满18周岁',
                    isExist: "身份证已存在"
                }
            }
        });


    });
});