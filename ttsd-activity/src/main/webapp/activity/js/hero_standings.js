require(['jquery', 'underscore','layerWrapper', 'template', 'logintip','jquery.ajax.extension','commonFun','register_common'], function($,_, layer, tpl) {
	$(function() {
		var browser = commonFun.browserRedirect(),
			$TodayAwards=$('#TodayAwards'),
			$investRankingButton=$('#investRanking-button'),
			$heroNext=$('#heroNext'),
			$heroPre=$('#heroPre'),
			todayDate= $.trim($TodayAwards.val()),
			ListTpl=$('#tplTable').html(),
			ListRender = _.template(ListTpl),
			bgListTpl=$('#bgtplTable').html(),
			bgListRender = _.template(bgListTpl);

		if (browser == 'mobile') {

            var urlObj=commonFun.parseURL(location.href);
            if(urlObj.params.tag=='yes') {
                $('.reg-tag-current').show();
            }
        }


		todayDate.replace(/-/gi,'')>=$('#startTime').val()?$heroNext.hide():false;

		//获取前一天或者后一天的日期
		function GetDateStr(date,AddDayCount) {
			var dd = new Date(date);
			dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
			var y = dd.getFullYear();
			var m = dd.getMonth()+1;//获取当前月份的日期
			var d = dd.getDate();

			return y + "-" + (m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d);
		}


		$investRankingButton.find('.change-btn').on('click',function(event) {
			var dateSpilt=$TodayAwards.val(),
				currDate;
			if(/heroPre/.test(event.target.id)) {
				currDate=GetDateStr(dateSpilt,-1); //前一天
				$heroNext.show();
			}
			else if(/heroNext/.test(event.target.id)){
				currDate=GetDateStr(dateSpilt,1); //后一天
				currDate.replace(/-/gi,'')>=todayDate.replace(/-/gi,'')?$heroNext.hide():$heroNext.show();
			}
			
			
			// currDate.replace(/-/gi,'')>=todayDate.replace(/-/gi,'')?$heroNext.hide():$heroNext.show();
			
			// currDate.replace(/-/gi,'')<=todayDate.replace(/-/gi,'')?$heroPre.hide():$heroPre.show();
			heroRank(currDate);
		});


		//英豪榜排名,今日投资排行
		function heroRank(date) {
			$.ajax({
				url: '/activity/hero-ranking/invest/' + date + '?activityCategory=NEW_HERO_RANKING',
				type: 'GET',
				dataType: 'json'
			})
			.done(function(data) {
				if(data.status) {
					var $nodataInvest=$('.table-list-item'),
						$contentRanking=$('#investRanking-tbody').parents('table');

					if(_.isNull(data.records) || data.records.length==0) {
						$nodataInvest.find('.table-content').html('<span class="date-over">暂无数据</span>');
						$contentRanking.hide();
						$('#bgItem').hide();
						return;
					}
					$contentRanking.show();
					$('#bgItem').show();
					data.type='invest';
					$('#investRanking-tbody').html(ListRender(data));
					$('#bgItem').html(bgListRender(data));
				}

			})
			.fail(function() {
				layer.msg('请求失败，请重试！');
			});
		}
		heroRank(todayDate);
        


	});
});