require(['jquery', 'layer', 'csrf', 'autoNumeric', 'commonFun'], function ($, layer) {
    $(function () {

        var $rechargeCon = $(".recharge-bind-card"),
            $fastRecharge=$('.fast-recharge',$rechargeCon),
            $rechargeForm = $('.recharge-form',$rechargeCon),
            $fastRechargeForm = $(".fast-recharge-form",$rechargeCon),
            $turnOnFast=$(".turn-on-fast-form",$rechargeCon);

        var tabElement = $('.payment-mode li'),
            rechargeInputAmountElement = $('.amount', $rechargeForm),
            rechargeAmountElement = $('input[name="amount"]', $rechargeForm),
            rechargeSubmitElement = $('.btn', $rechargeForm),

            fastRechargeInputAmountElement = $('.amount', $fastRechargeForm),
            fastRechargeAmountElement = $('input[name="amount"]', $fastRechargeForm),
            fastRechargeSubmitElement = $('.btn', $fastRechargeForm),
            bankElement = $('.e-bank-recharge ol li'),
            turnOnFastSubmitElement = $('input[type="submit"]',$turnOnFast);

        if (rechargeInputAmountElement) {
            rechargeInputAmountElement.autoNumeric("init");
            rechargeInputAmountElement.keyup(function () {
                var amount = parseFloat(rechargeInputAmountElement.autoNumeric("get"));
                if (isNaN(amount) || amount < 0.01) {
                    rechargeSubmitElement.prop('disabled', true).removeClass('btn-normal');
                } else {
                    rechargeSubmitElement.prop('disabled', false).addClass('btn-normal');
                }
            });
            //网银充值提交
            rechargeSubmitElement.click(function () {
                var amount = rechargeInputAmountElement.autoNumeric("get");
                rechargeAmountElement.val(amount);

                layer.open({
                    type: 1,
                    title: '登录到联动优势支付平台充值',
                    area: ['560px', '270px'],
                    shadeClose: true,
                    content: $('#popRecharge')
                });
            });
        }

        if (fastRechargeInputAmountElement) {
            fastRechargeInputAmountElement.autoNumeric("init");
            fastRechargeInputAmountElement.keyup(function () {
                var amount = parseFloat(fastRechargeInputAmountElement.autoNumeric("get"));
                if (isNaN(amount) || amount < 0.01) {
                    fastRechargeSubmitElement.prop('disabled', true).removeClass('btn-normal');

                } else {
                    fastRechargeSubmitElement.prop('disabled', false).addClass('btn-normal');
                }
            });
            //快捷充值提交
            fastRechargeSubmitElement.click(function () {
                var amount = fastRechargeInputAmountElement.autoNumeric("get");
                fastRechargeAmountElement.val(amount);
                layer.open({
                    type: 1,
                    title: '登录到联动优势支付平台充值',
                    area: ['500px', '290px'],
                    shadeClose: true,
                    content: $('#popRecharge')
                });
            });
            //开通快捷支付
            turnOnFastSubmitElement.click(function() {
                layer.open({
                    type: 1,
                    title: '开通快捷支付功能',
                    area: ['500px', '180px'],
                    shadeClose: true,
                    content: $('#openFastRecharge')
                });
            });
        }
        if ($(".bind-card-nav")) {
            $(".bind-card-nav .btn", $rechargeCon).click(function () {
                window.location.href = $(this).data('url');
            });
        }

        //tab切换

        tabElement.click(function (index) {
            tabElement.removeClass("active");
            var self = $(this),
                num=tabElement.index(this),
                $rechargeCon=$('.recharge-content'),
                $fastRecharge=$('.fast-recharge',$rechargeCon),
                $bankRecharge=$('.e-bank-recharge',$rechargeCon);
            self.addClass("active").siblings('li').removeClass('active');
            if(num==0) {
                $fastRecharge.addClass('active');
                $bankRecharge.removeClass('active');
            }
            else {
                $fastRecharge.removeClass('active');
                $bankRecharge.addClass('active');
            }

        });

    });
});