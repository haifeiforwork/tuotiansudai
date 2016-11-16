require(['jquery', 'csrf', 'bootstrap', 'bootstrapSelect', 'bootstrapDatetimepicker', 'jquery-ui'], function ($) {
    $(function () {
        //渲染select表单
        $('.selectpicker').selectpicker();

        $('.approve-btn').on('click', function () {
            if (confirm("确认审核？")) {
                var messageId = $(this).attr('data-messageId');
                var url = "/message-manage/approve/" + messageId;
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json'
                }).complete(function (data) {
                    if (data.status) {
                        location.reload();
                    } else {
                        alert(data.data.message);
                    }
                })
            }
        });
        $('.reject-btn').on('click', function () {
            if (confirm("确认驳回？")) {
                var messageId = $(this).attr('data-messageId');
                var url = "/message-manage/reject/" + messageId;
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json'
                }).complete(function (data) {
                    if (data.status) {
                        location.reload();
                    } else {
                        alert(data.data.message);
                    }
                })
            }
        });
        $('.delete-btn').on('click', function () {
            if (confirm("确认删除？")) {
                var messageId = $(this).attr('data-messageId');
                var url = "/message-manage/delete/" + messageId;
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json'
                }).complete(function (data) {
                    if (data.status) {
                        location.reload();
                    } else {
                        alert(data.data.message);
                    }
                })
            }
        });
    });
});