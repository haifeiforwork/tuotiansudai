require(['jquery', 'csrf', 'jquery-ui', 'bootstrap'], function ($) {

    $('.btn-submit').click(function(){
        var mobile = $('#mobile').val();
        var reg = /^\d{11}$/;

        if(!reg.test(mobile)){
            $('.web-error-message').show();
            $('.console-error-message').hide();
            $('.message').html('手机号码应为11位数字！');
            return false;
        }

        $('.web-error-message').hide();
        $('#confirm-modal').modal('show');
        return false;
    });

    $('#referrer').autocomplete({
        minLength: 3,
        source: function (query, process) {
            $.get('/user/' + query.term + '/search', function (respData) {
                return process(respData);
            });
        },
        change: function (event, ui) {
            if (!ui.item) {
                this.value = '';
            }
        }
    });

    $('#confirm-modal .btn-submit').click(function () {
        $("form").submit();
    });

    $("input[type='reset']").click(function() {
        location.reload();
        return false;
    });
});