require(['jquery', 'bootstrap', 'bootstrapDatetimepicker','bootstrapSelect','Validform','Validform_Datatype','layer', 'layer-extend', 'layerWrapper', 'csrf'], function ($) {
    $(function () {
        var $body = $('body'),
            $confirmBtn = $('.confirm-btn'),//conirm button
            $inactiveBtn = $('.inactive-btn'),
            $tooltip = $('.add-tooltip'),
            $couponDelete = $('.coupon-delete'),
            $tipCom = $('.tip-container'),
            $selectDom=$('#operationType');

        $tooltip.length ? $tooltip.tooltip() : false;
        $selectDom.selectpicker();
        $couponDelete.on('click', function () {
            var $self = $(this),
                thisLink = $self.attr('data-link');
            if (!confirm("是否确认执行此操作?")) {
                return;
            } else {
                $.ajax({
                    url: thisLink,
                    type: 'DELETE',
                    dataType: 'json'
                })
                    .done(function (res) {
                        if (res.data.status) {
                            $self.closest('tr').remove();
                        } else {
                            $tipCom.show().find('.txt').text('操作失败！');
                        }
                    })
                    .fail(function (res) {
                        $tipCom.show().find('.txt').text('请求发送失败，请刷新重试！');
                    });
            }
        });
        $('body').delegate('.inactive-btn', 'click', function (e) {
            e.preventDefault();
            var $self = $(this),
                $parentTd = $self.parents('td'),
                thisId = $self.attr('data-id');//data id
            var couponType = $self.attr('data-type');
            if (!confirm("是否确认执行此操作?")) {
                return;
            } else {
                $.ajax({
                    url: '/activity-manage/coupon/' + thisId + '/inactive',
                    type: 'POST',
                    dataType: 'json'
                })
                    .done(function (res) {
                        if (res.data.status) {
                            $parentTd.html('<i class="check-btn"></i><a class="loan_repay confirm-btn" href="javascript:void(0)" data-type="' + couponType + '" data-id="' + thisId + '">确认生效</a>');
                            $parentTd.prev().html('-');
                        } else {
                            $tipCom.show().find('.txt').text('操作失败！');
                        }
                    })
                    .fail(function (res) {
                        $self.addClass('confirm-btn').text('操作失败');
                        $tipCom.show().find('.txt').text('请求发送失败，请刷新重试！');
                    });
            }
        });

        //confirm event
        $('body').delegate('.confirm-btn', 'click', function (e) {
            e.preventDefault();
            var $self = $(this),
                $parentTd = $self.parents('td'),
                thisId = $self.attr('data-id');//data id
            var couponType = $self.attr('data-type');
            if (!confirm("是否确认执行此操作?")) {
                return;
            } else {
                $.ajax({
                    url: '/activity-manage/coupon/' + thisId + '/active',
                    type: 'POST',
                    dataType: 'json'
                })
                    .done(function (res) {
                        if (res.data.status) {
                            $parentTd.html('<i class="check-btn add-check"></i><button class="loan_repay already-btn btn-link inactive-btn" data-id="' + thisId + '">已生效</button>');

                            $parentTd.prev().html('-');
                        } else {
                            $tipCom.show().find('.txt').text('操作失败！');
                        }
                    })
                    .fail(function (res) {
                        $self.addClass('confirm-btn').text('操作失败');
                        $tipCom.show().find('.txt').text('请求发送失败，请刷新重试！');
                    });
            }
        })

    });

    $('.detail-redis').on('click', function () {
        var $this = $(this);
        var link = $this.attr('data-url');
        $.ajax({
            url: link,
            type: 'POST',
            dataType: 'JSON'
        })
            .done(function (res) {
                $('.see-detail').show();
                var $table = $('.see-detail').find('table');
                $table.find('tr').remove();
                if (res[0]) {
                    var failed = new Array();
                    failed = res[0].split(',');
                    for (i = 0; i < failed.length; i++) {
                        $table.append('<tr><td class="text-red">' + failed[i] + '</td></tr>');
                    }
                }
                if (res[1]) {
                    var success = new Array();
                    success = res[1].split(',');
                    for (i = 0; i < success.length; i++) {
                        $table.append('<tr><td>' + success[i] + '</td></tr>');
                    }
                }
            })
            .fail(function (res) {
                $this.addClass('confirm-btn').text('操作失败');
            });
    });

    $('.close-btn').on('click', function () {
        $(this).parents('.see-detail').hide();
    });

    $('.interest-coupons').click(function () {
        location.href = "/export/interest-coupons?"+$('#couponList').serialize();
    });

    $('#amount').blur(function () {
        var _this = $(this),
            text = _this.val(),
            isNumber = /^\d+(\.\d+)?$/;
        if (!isNumber.test(text)) {
            _this.val('');
        }
    });

});