require(['jquery','loadEcharts','bootstrapDatetimepicker'],function($,loadEcharts) {

    var initStartDate,initEndDate,
        $sideNav=$('.sidenav'),
        winScrollTop,
        headerHeight=$('#top').height()+20,
        panelHeight=$('.panel-success').eq(0).height();
        $('.panel-success').eq(0).height()

    $(window).scroll(function() {

        winScrollTop=$(window).scrollTop();
        if(winScrollTop>headerHeight) {
            $sideNav.css({'position':'fixed','left':'15px','top':'10px','width':'14.6%'});
        }
        else {
            $sideNav.removeAttr('style');
        }
    });
    $sideNav.find('li').on('click',function(index) {
        var num=$sideNav.find('li').index(this),heightHack;
        $(this).addClass('active').siblings('li').removeClass('active');
        switch(num) {
            case 1:
                heightHack=20;
                break;
            case 2:
                heightHack=40;
                break;
            default:
                heightHack=0;
                break;
        }
        $(window).scrollTop(headerHeight+panelHeight*num+heightHack);

    });
    $('.start-date,.end-date').datetimepicker({
        format: 'YYYY-MM-DD',
        maxDate: 'now'
    });

    loadEcharts.ChartsProvince(function(data) {
        var provinceList=[],i= 0,len=data.length;
        provinceList.push('<option value="">请选择</option>');
        for(;i<len;i++) {
            provinceList.push('<option value="'+data[i]+'">'+data[i]+'</option>');
        }
        $('select[name="province"]').each(function(index,option) {
            $(option).empty().append(provinceList.join(''));

        });
    });

    initStartDate=loadEcharts.datetimeFun.getBeforeDate(6);
    initEndDate=loadEcharts.datetimeFun.getBeforeDate(0);

    $('.start-date').val(initStartDate);
    $('.end-date').val(initEndDate);

    function showReport(form,url,reportbox,name) {
        var Btn=$(form).find(':button');
        Btn.click(function() {
            var dataFormat=$(form).serialize();
            $.ajax({
                type: 'GET',
                data:dataFormat,
                url: url,
                dataType: 'json'
            }).done(function (data) {
                /*sort by date*/
                data.sort(function(a,b){
                    var a1=a.name.split('-'),
                        b1= b.name.split('-'),
                        o1 = new Date().setFullYear(a1[0],a1[1],a1[2]),
                        o2=new Date().setFullYear(b1[0],b1[1],b1[2]);
                    return o1- o2;
                });
                var option = loadEcharts.ChartOptionTemplates.Lines(data, name, true),
                    container =document.getElementById(reportbox),
                    opt = loadEcharts.ChartConfig(container, option);
                loadEcharts.Charts.RenderChart(opt);
            });
        }).trigger('click');

    }

    /*用户注册时间分布*/
    showReport('#formUserDateReport','/bi/user-register-trend','userDateDistribution','用户(人)');

    /*用户充值时间分布*/
    showReport('#formUserRechargeReport','/bi/user-recharge-trend','UserRechargeDistribution','用户充值(元)');

    /*用户提现时间分布*/
    showReport('#formWithdrawReport','/bi/user-withdraw-trend','userWithdrawDistribution','用户提现(元)');

    /*用户账户余额时间分布*/
    showReport('#formUserAccountReport','/bi/user-account-trend','userAccountDistribution','用户账户余额(元)');



});
