//var style2 = require('../style/sass/answer.scss');

var $=require('jquery');

var $answerContainer=$('.answer-container');
var $answerButton=$('.answer-button',$answerContainer);
var $toAnswerBox=$('.to-answer-box',$answerContainer);

var $myQAnswer=$('#myQAnswer');
$answerButton.find('button').on('click',function(index) {
    $toAnswerBox.toggle();
});

$('#my-questions-tab').find('li').click(function(index) {
    var $this=$(this),
     num =$this.index();
    $this.addClass('active').siblings('li').removeClass('active');
    $myQAnswer.find('.answers-box').eq(num).show().siblings('.answers-box').hide();
});