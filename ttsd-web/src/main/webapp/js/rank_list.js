require(['jquery','rotate','layerWrapper','jquery.ajax.extension'], function($,rotate,layer) {
	var bRotateTd = false,
		bRotateCd = false,
		$beanBtn=$('#beanBtn li'),
		$awardBtn=$('#awardBtn li'),
		$pointerTd=$('#pointerTd'),
		$pointerCd=$('#pointerCd'),
		$rotateTd=$('#rotateTd'),
		$rotateCd=$('#rotateCd'),
		$giftRecord=$('.gift-record li');
	//change rank list
	$beanBtn.on('click', function(event) {
		var $self=$(this),
			index=$self.index();
		$self.addClass('active').siblings('li').removeClass('active');
		$('#beanCom').find('.leader-list:eq('+index+')').addClass('active')
			.siblings('.leader-list').removeClass('active');
	});

	//change award list
	$awardBtn.on('click', function(event) {
		var $self=$(this),
			index=$self.index();
		$self.addClass('active').siblings('li').removeClass('active');
		$('#awardCom').find('.leader-list:eq('+index+')').addClass('active')
			.siblings('.leader-list').removeClass('active');
	});

	//change award record btn
	$giftRecord.on('click', function(event) {
		var $self=$(this),
			index=$self.index();
		$self.addClass('active').siblings('li').removeClass('active');
		$('#recordList').find('.record-model:eq('+index+')').addClass('active')
			.siblings('.record-model').removeClass('active');
	});
	//td click
	$pointerTd.on('click', function(event) {
		event.preventDefault();
        var $self=$(this),
            isLogin=$self.attr('data-is-login');
        if(isLogin!='true'){
            $('#tipList').show();
            $('#noLogin').show();
        }else{
    		if(bRotateTd)return;
    		$.ajax({
    			url: '/activity/drawTianDou',
    			type: 'POST',
    			dataType: 'json'
    		})
    		.done(function(res) {
                if(res.data.returnCode==0){
                    var item = res.data.tianDouPrize;
                    switch (item) {
                        case 'Cash20':
                            rotateFnTd(0, 56, '20元现金');
                            break;
                        case 'Iphone6s':
                            rotateFnTd(1, 120, 'iPhone 6s Plus');
                            break;
                        case 'JingDong300':
                            rotateFnTd(2, 200, '300元京东购物卡');
                            break;
                        case 'InterestCoupon5':
                            rotateFnTd(3, 260, '0.5%加息券');
                            break;
                        case 'MacBook':
                            rotateFnTd(4, 337, 'MacBook Air');
                            break;
                    }
                }else if(res.data.returnCode==1){
                    $('#tipList').show();
                    $('#TDnoUse').show();
                }else{
    			    $('#tipList').show();
                    $('#noLogin').show();
                }
    		})
    		.fail(function() {
    			layer.msg('请求失败'); 
    		});
        }
	});
    //close btn
    $('body').on('click', '.go-close', function(event) {
        event.preventDefault();
        var $self=$(this),
            $parent=$self.parents('.tip-list'),
            $tipDom=$parent.find('.tip-dom');
        $parent.hide();
        $tipDom.hide();
    });
    function rotateFnTd(awards, angles, txt){
        bRotateTd = !bRotateTd;
        $('#rotateTd').stopRotate();
        $('#rotateTd').rotate({
            angle:0,
            animateTo:angles+1800,
            duration:8000,
            callback:function (){
                $('#tipList').show();
                switch (awards) {
                    case 0:
                        $('#twentyRMB').show();
                        break;
                    case 1:
                        $('#iphone6s').show();
                        break;
                    case 2:
                        $('#jdCard').show();
                        break;
                    case 3:
                        $('#jiaxi').show();
                        break;
                    case 4:
                        $('#macbookAir').show();
                        break;
                }
                bRotateTd = !bRotateTd;
            }
        })
    }
	//cd click
	$pointerCd.on('click', function(event) {
		event.preventDefault();
        var $self=$(this),
            isLogin=$self.attr('data-is-login');
        if(isLogin!='true'){
            $('#tipList').show();
            $('#noLogin').show();
        }else{
            if(bRotateCd)return;
            $.ajax({
                url: '/activity/point-lottery',
                type: 'POST',
                dataType: 'json'
            })
            .done(function(data) {
                if(bRotateCd)return;
                switch (data) {
                    case 'PointNotEnough':
                        $('#tipList').show();
                        $('#NoCdbean').show();
                        break;
                    case 'Cash5':
                        rotateFnCd(1, 80, '现金5元');
                        break;
                    case 'AlreadyLotteryNotShare':
                        $('#tipList').show();
                        $('#oneDay').show();
                        break;
                    case 'ThankYou':
                        rotateFnCd(3, 173, '谢谢参与');
                        break;
                    case 'Cash2':
                        rotateFnCd(4, 210, '现金2元');
                        break;
                    case 'InterestCoupon2':
                        rotateFnCd(5, 255, '0.2%加息券');
                        break;
                    case 'AlreadyLotteryShare':
                        $('#tipList').show();
                        $('#onlyTwice').show();
                        break;
                    case 'InvestCoupon3000':
                        rotateFnCd(7, 355, '3000元体验金');
                        break;
                }
            })
            .fail(function() {
                layer.msg('请求失败'); 
            });
        }
	});

    function rotateFnCd(awards, angles, txt){
        bRotateCd = !bRotateCd;
        $('#rotateCd').stopRotate();
        $('#rotateCd').rotate({
            angle:0,
            animateTo:angles+1800,
            duration:8000,
            callback:function (){
                $('#tipList').show();
                switch (awards) {
                    case 1:
                        $('#twentyRMB').show();
                        break;
                    case 3:
                        $('#iphone6s').show();
                        break;
                    case 4:
                        $('#jdCard').show();
                        break;
                    case 5:
                        $('#jiaxi').show();
                        break;
                    case 7:
                        $('#macbookAir').show();
                        break;
                }
                bRotateCd = !bRotateCd;
            }
        })
    };
    


	//share event
	window._bd_share_config = {
		"common": {
			"bdSnsKey": {},
			"bdText": "",
			"bdMini": "2",
			"bdPic": "",
			"bdStyle": "0",
			"bdSize": "16",
			onAfterClick:function(cmd){
				console.log("我被分享了！");
			}
		},
		"share": {}
	};
	with(document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];


});