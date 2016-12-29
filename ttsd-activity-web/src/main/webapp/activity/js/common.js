define(['jquery'], function ($) {

    var commonFun={
        //加密
        compile:function (strId,realId) {
            var realId=realId+'';
            var strIdObj=strId.split(''),
                realLen=realId.length;
            for(var i=0;i<11;i++) {
                strIdObj[2*i+2]=realId[i]?realId[i]:'a';
            }
            return strIdObj.join('');

        },
        //解密
        uncompile:function (strId) {
            var strId=strId+'';
            var strIdObj=strId.split(''),
                realId=[];
            for(var i=0;i<11;i++) {
                realId[i]=strIdObj[2*i+2];
            }

            var stringRealId=realId.join(''),
                getNum=stringRealId.match(/\d/gi);
            return getNum.join('');
        },

        /* init radio style */
        initRadio:function($radio,$radioLabel) {
            var numRadio=$radio.length;
            if(numRadio) {
                $radio.each(function(key,option) {
                    var $this=$(this);
                    if($this.is(':checked')) {
                        $this.next('label').addClass('checked');
                    }
                    $this.next('label').click(function() {
                        var $thisLab=$(this);
                        if(!/checked/.test(this.className)) {
                            $radioLabel.removeClass('checked');
                            $thisLab.addClass('checked');
                        }
                    });
                });

            }
        },

        // 验证身份证有效性
        IdentityCodeValid:function(code) {
            var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
            var pass= true;

            if (!code || !/\d{17}[\d|x]/i.test(code)) {
                pass = false;
            }

            else if(!city[code.substr(0,2)]){
                pass = false;
            }
            else{
                //18位身份证需要验证最后一位校验位
                if(code.length == 18){
                    code = code.split('');
                    //∑(ai×Wi)(mod 11)
                    //加权因子
                    var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                    //校验位
                    var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                    var sum = 0;
                    var ai = 0;
                    var wi = 0;
                    for (var i = 0; i < 17; i++)
                    {
                        ai = code[i];
                        wi = factor[i];
                        sum += ai * wi;
                    }
                    if(parity[sum % 11] != code[17]){
                        pass =false;
                    }
                }
            }
            return pass;
        },

        popWindow:function(contentHtml,area) {
            var $shade=$('<div class="shade-body-mask"></div>');
            var $popWindow=$(contentHtml),
                size= $.extend({width:'460px',height:'370px'},area);
            $popWindow.css({
                width:size.width,
                height:size.height
            });
            var adjustPOS=function() {
                var scrollHeight=document.body.scrollTop || document.documentElement.scrollTop,
                    pTop=$(window).height()-$popWindow.height(),
                    pLeft=$(window).width()-$popWindow.width();
                $popWindow.css({'top':pTop/2,left:pLeft/2});
                $shade.height($('body').height());
                $('body').append($popWindow).append($shade);
            }
            adjustPOS();


            $('.close-btn,.go-close',$popWindow).on('click',function() {
                $popWindow.remove();
                $shade.remove();

            })
        },

        // 验证用户是否处于登陆状态
        isUserLogin:function() {
            var LoginDefer=$.Deferred(); //在函数内部，新建一个Deferred对象
            $.ajax({
                url: '/isLogin',
                type: 'GET'
            })
                .done(function(data) {
                    if(data) {
                        //如果data有值，说明token已经过期，用户处于未登陆状态，并且需要更新token
                        LoginDefer.reject(data);
                        $("meta[name='_csrf']").remove();
                        $('head').append($(data.responseText));

                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        $(document).ajaxSend(function (e, xhr, options) {
                            xhr.setRequestHeader(header, token);
                        });
                    }
                    else {
                        //如果data为空，说明用户处于登陆状态，不需要做任何处理
                        LoginDefer.resolve();
                    }
                })
                .fail(function() {
                    LoginDefer.reject();
                });

            return LoginDefer.promise(); // 返回promise对象
        }
    };

    return commonFun;
});




