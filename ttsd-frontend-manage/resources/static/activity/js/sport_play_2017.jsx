require("activityStyle/sport_play_2017.scss");
let commonFun= require('publicJs/commonFun');
require('publicJs/login_tip');
let drawCircle = require('activityJsModule/gift_circle_draw');
let tpl = require('art-template/dist/template');



let $sportPlayContainer = $('#sportPlayContainer'),
    sourceKind = globalFun.parseURL(location.href);
let topimg=require('../images/2017/sport-play/top-img.jpg'),
    topimgPhone=require('../images/2017/sport-play/top-img-phone.jpg');

$sportPlayContainer.find('.top-img .media-pc').attr('src',topimg).siblings('.media-phone').attr('src',topimgPhone);

let drawBtn=require('../images/2017/sport-play/draw-btn.png');
$sportPlayContainer.find('.draw-model img').attr('src',drawBtn);


let $pointerImg = $('.draw-btn', $sportPlayContainer);
$pointerImg.on('click', function () {
    //未登录
    $.when(commonFun.isUserLogin())
    .done(function () {
        getGift();
    })
    .fail(function () {
        if (sourceKind.params.source == 'app') {
            location.href = "/login";
        } else {
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                area: ['auto', 'auto'],
                content: $('#loginTip')
            });
        }
    });
});

function getGift() {
    //判断是否正在抽奖
    if ($pointerImg.hasClass('lottering')) {
        return;//不能重复抽奖
    }
    $pointerImg.addClass('lottering');
    //延迟1秒抽奖
    setTimeout(function () {
        commonFun.useAjax({
            dataType: 'json',
            url:'/activity/exercise-work/exercise-work-draw',
            data: {
                'activityCategory': 'EXERCISE_WORK_ACTIVITY'
            }
        },function(data) {
            console.log(data);
            $pointerImg.removeClass('lottering');
            if (data.returnCode == 2) {
                //判断是否需要弹框登陆
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['auto', 'auto'],
                    content: $('#loginTip')
                });
            } else {
                $('#lotteryTip .lottery-content').html(tpl('lotteryTipTpl', data));
                layer.open({
                  type: 1,
                  title: false,
                  closeBtn: 0,
                  area: ['450px', '230px'],
                  content: $('#lotteryTip') 
                });
            }
        });
    }, 1000);
}


// 选择礼品
$sportPlayContainer.find('.gift-list .select-item').on('click',  function(event) {
    event.preventDefault();
    let $self=$(this);
    if(!$self.hasClass('active')){
        $self.addClass('active').siblings('.select-item').removeClass('active')
        .closest('.gift-item').siblings().find('.select-item').removeClass('active');
    }
});

// 兑换商品
$sportPlayContainer.find('.gift-item .text-item').on('click',  function(event) {
    event.preventDefault();
    let $self=$(this),
        isSelect=$self.closest('.gift-item').find('.select-item').hasClass('active'),
        selectGift=$self.closest('.gift-item').find('.select-item.active').attr('data-name');
    
    if(isSelect){
        commonFun.useAjax({
            dataType: 'json',
            url:'/activity/exercise-work/exchange-prize',
            data: {
                'exchangePrize': selectGift
            }
        },function(data) {
            console.log(data);
            if (data.returnCode == 4) {
                //判断是否需要弹框登陆
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['auto', 'auto'],
                    content: $('#loginTip')
                }); 
            }else {
                $('#exchangeTip').html(tpl('exchangeTipTpl', data));
                
            }
        });
    }else{
        $('#exchangeTip').html(tpl('exchangeTipTpl', {'returnCode':5}));
    }
    layer.open({
      type: 1,
      title: false,
      closeBtn: 0,
      area: ['470px', '300px'],
      content: $('#exchangeTip') 
    });
});
$('body').on('click', '.close-tip', function(event) {
    event.preventDefault();
    layer.closeAll();
});


