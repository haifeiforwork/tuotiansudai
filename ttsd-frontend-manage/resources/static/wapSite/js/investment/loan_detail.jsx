require('wapSiteStyle/investment/experience_detail.scss');
require('wapSiteStyle/investment/loan_detail.scss');

let $loanDetail = $('#loanDetail'),
    $iconHelp = $('.icon-help',$loanDetail);

$iconHelp.on('click',function() {
    $('.invest-refer-box',$loanDetail).toggle();
})