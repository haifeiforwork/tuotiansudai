require(['jquery'], function($) {
	var bRotateTd = false,
		bRotateCd = false;
	function rotateTimeOut(){
        $('#rotate').rotate({
            angle:0,
            animateTo:2160,
            duration:8000,
            callback:function (){
                alert('网络超时，请检查您的网络设置！');
            }
        });
    };
    function rotateFn(awards, angles, txt){
        bRotate = !bRotate;
        $('#rotate').stopRotate();
        $('#rotate').rotate({
            angle:0,
            animateTo:angles+1800,
            duration:8000,
            callback:function (){
                alert(txt);
                bRotate = !bRotate;
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