require(['jquery','layerWrapper', 'moment', 'pagination', 'mustache', 'text!/tpl/my-treasure-table.mustache'],
 function ($, layer, Mustache, moment, pagination, treasureListTemplate) {
    $(function() {
        var $body=$('body'),
            $navLi=$('.column-title .title-navli'),
            $listTab=$('.list-tab');

        $navLi.on('click',function(event) {
            event.preventDefault();
            var $self=$(this),
                index=$self.index();
            $navLi.removeClass('active');
            $self.addClass('active');
            $listTab.removeClass('tab-show');
            $('.list-tab:eq('+index+')').addClass('tab-show');
        });
        $body.on('click','.select-item',function(event) {
            event.preventDefault();
            var $self=$(this),
                dataStatus=$self.attr('data-status');
            $self.addClass('current').siblings('.select-item').removeClass('current');
            templateData(dataStatus);
        });

        $('.invest-list').on('mouseenter','.project-name',function() {
            layer.closeAll('tips');
            if($(this).text().length>15){
                layer.tips($(this).text(), $(this), {
                    tips: [1, '#efbf5c'],
                    time: 2000,
                    tipsMore: true,
                    area: 'auto',
                    maxWidth: '500'
                });
            }
        });

        function templateData(data){
            $.ajax({
                url: 'http://192.168.100.70:8080/coupon/use-record?loginName=shenjiaojiao&index=1&pageSize=10',
                type: 'POST',
                dataType: 'json',
                data: {param1: data}
            })
            .done(function(json) {
                var html = Mustache.render(treasureListTemplate, json);
                console.log(json);
                $('.invest-list').html(html);
            })
            .fail(function() {
                console.log("error");
            });
        }
        
        templateData($('.select-item.current').attr('data-status'));
    });
});
