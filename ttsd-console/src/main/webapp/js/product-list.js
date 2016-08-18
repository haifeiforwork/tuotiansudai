require(['jquery','bootstrap', 'bootstrapDatetimepicker','csrf'], function($) {
    $(function () {
        //confirm event
        $('body').delegate('.confirm-btn','click',function(e) {
            e.preventDefault();
            var $self=$(this),
                $parentTd = $self.parents('td'),
                thisId=$self.attr('data-id');//data id
            if (!confirm("是否确认执行此操作?")) {
                return;
            }else{
                $.ajax({
                        url: '/product-manage/' + thisId +'/active',
                        type: 'POST',
                        dataType: 'json'
                    })
                    .done(function(res) {
                        if(res.data.status){
                            $parentTd.html('<label><i class="check-btn add-check"></i><button class="loan_repay already-btn btn-link inactive-btn" data-id="'+thisId+'">已生效</button></label>');
                            $parentTd.prev().html('-');
                        }else{
                            $tipCom.show().find('.txt').text('操作失败！');
                        }
                    })
                    .fail(function(res) {
                        $self.addClass('confirm-btn').text('操作失败');
                        $tipCom.show().find('.txt').text('请求发送失败，请刷新重试！');
                    });
            }
        })


        $('body').delegate('.inactive-btn', 'click', function (e) {
            e.preventDefault();
            var $self = $(this),
                $parentTd = $self.parents('td'),
                thisId = $self.attr('data-id'),//data id
                thisCount = $self.attr('data-value');
            if (!confirm("是否确认执行此操作?")) {
                return;
            } else {
                $.ajax({
                        url: '/product-manage/' + thisId +'/inactive',
                        type: 'POST',
                        dataType: 'json'
                    })
                    .done(function (res) {
                        if (res.data.status) {
                            $parentTd.html('<i class="check-btn"></i><a class="loan_repay confirm-btn" href="javascript:void(0)" data-id="' + thisId + '" data-value="' + thisCount + '">确认生效</a>');
                            if(thisCount != "0")
                            {
                                $parentTd.prev().html('-');
                            }
                            else{
                                $parentTd.prev().html('<a href="/product-manage/'+ thisId + '/edit" class="btn-link">编辑</a>');
                            }

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
                thisId = $self.attr('data-id'),//data id
                thisCount = $self.attr('data-value');//data value
            if (!confirm("是否确认执行此操作?")) {
                return;
            } else {
                $.ajax({
                        url: '/product-manage/' + thisId +'/active',
                        type: 'POST',
                        dataType: 'json'
                    })
                    .done(function (res) {
                        if (res.data.status) {
                            $parentTd.html('<i class="check-btn add-check"></i><button class="loan_repay already-btn btn-link inactive-btn" data-id="' + thisId + '" data-value="' + thisCount + '">已生效</button>');
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




    });
});