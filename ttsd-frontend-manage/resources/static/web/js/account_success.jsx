let commonFun= require('publicJs/commonFun');

let $successBox= $('#successBox');
let $countTime = $('.count-time',$successBox);

commonFun.countDownLoan({
    btnDom:$countTime,
    time:3
},function() {
    window.location.href = '/personal-info';
});

