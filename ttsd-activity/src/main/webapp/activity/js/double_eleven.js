require(['jquery', 'underscore', 'layerWrapper', 'template', 'jquery.ajax.extension', 'commonFun', 'register_common', 'nine_lottery','logintip'], function($, _, layer, tpl) {
    $(function() {
        var browser = commonFun.browserRedirect(),
            timeCount=0;


        if (browser == 'mobile') {
            var urlObj = commonFun.parseURL(location.href);
            if (urlObj.params.tag == 'yes') {
                $('.reg-tag-current').show();
            }
        }

        function getRTime() {
            $('#timeCount li').each(function(index, el) {
                var $self=$(this),
                    _this=$self.find('.time-text span'),
                    endtimeText=_this.attr('data-end'),
                    nowtimeText=_this.attr('data-now'),
                    activityEnd=_this.attr('data-activityEnd'),
                    dateText=_this.attr('data-date').replace('-','/'),
                    EndTime = new Date('2016/'+dateText+' '+endtimeText),
                    NowTime = new Date(),
                    t = EndTime.getTime() - NowTime.getTime(),
                    h = Math.floor(t / 1000 / 60 / 60 % 24),
                    m = Math.floor(t / 1000 / 60 % 60),
                    s = Math.floor(t / 1000 % 60);
                if(new Date('2016/'+dateText).getTime()>new Date(activityEnd).getTime()){
                    clearInterval(timer);
                    $self.addClass('end').find('.time-text span').text('活动已过期');
                    $self.find('.btn-item a').attr('href','javascript:void(0)');
                }else{
                    if(t>0){
                        _this.text((h>=10?h:'0'+h) + ":" + (m>=10?m:'0'+m) + ":" + (s>=10?s:'0'+s));
                    }else{
                        $self.addClass('active') &&_this.html('火热进行中');
                    }
                }
            });
        }
        getRTime();
        var timer=setInterval(getRTime, 1000);

    });
});