let commonFun = require('publicJs/commonFun');

let $downTime = $('#downTime');

commonFun.countDownLoan({
    btnDom: $downTime,
    time: 5,
    isAfterText:'',
    textCounting: 's'
}, function () {
    window.location = "/";
});
