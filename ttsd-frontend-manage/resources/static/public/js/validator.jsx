var commonFun=require('publicJs/commonFun');

function createElement(element,errorMsg) {
    if(element && element.nextElementSibling) {
        element.nextElementSibling.innerHTML=errorMsg;
        return;
    }
    var span=document.createElement("span");
    span.className="error";
    span.innerHTML=errorMsg;
    element && element.parentElement.appendChild(span);
}
function removeElement(element) {
    (element && element.nextElementSibling) && element.parentElement.removeChild(element.nextElementSibling);
}
/*******策略对象********/
var strategies = {
    isNonEmpty: function(errorMsg,showErrorAfter) {
        if (this.value === '') {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    minLength: function(errorMsg,length,showErrorAfter) {
        if (this.value.length < Number(length)) {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    maxLength: function(errorMsg,length,showErrorAfter) {
        if (this.value.length > Number(length)) {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    isNumber:function(errorMsg,length,showErrorAfter) {
        if(length) {
            var reg=new RegExp('^\\d{'+length+'}$','g');
            if(reg.test(this.value)) {
                globalFun.removeClass(this,'error');
                globalFun.addClass(this,'valid');
                showErrorAfter && removeElement(this);
            }
            else {
                globalFun.addClass(this,'error');
                showErrorAfter && createElement(this,errorMsg);
                return errorMsg;
            }
        }
        else {
            //判断是非为数字，无需固定判断长度
           if(/^\d+[.]{0,1}\d*$/.test(this.value)) {
               globalFun.removeClass(this,'error');
               globalFun.addClass(this,'valid');
               showErrorAfter && removeElement(this);
           }
           else {
               globalFun.addClass(this,'error');
               showErrorAfter && createElement(this,errorMsg);
               return errorMsg;
           }

        }
    },
    isChinese:function (errorMsg,showErrorAfter) {
        if(/^[\u4E00-\u9FA5]+$/.test(this.value)) {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
        else {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }

    },
    equalLength:function(errorMsg,length,showErrorAfter) {
        if (this.value.length!=Number(length)) {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);

            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    checkPassword:function(errorMsg,showErrorAfter) {
        var regBool=/^(?=.*[^\d])(.{6,20})$/.test(this.value);
        if (!regBool) {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    isMobile: function(errorMsg,showErrorAfter) {
        //只验证手机号不验证是非为空
        if(this.value=='') {
            return '';
        }
        if (!/(^1[0-9]{10}$)/.test(this.value)) {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    identityValid:function(errorMsg,showErrorAfter) {
        //验证身份证号
        var cardValid=commonFun.IdentityCodeValid(this.value);
        if(!cardValid) {
            globalFun.addClass(this,'error');
            showErrorAfter && createElement(this,errorMsg);
            return errorMsg;
        }
        else {
            globalFun.removeClass(this,'error');
            globalFun.addClass(this,'valid');
            showErrorAfter && removeElement(this);
        }
    },
    ageValid:function(errorMsg,showErrorAfter) {
        //验证年龄是否满18
        var cardValid=commonFun.IdentityCodeValid(this.value);
        if(cardValid) {
            var ageValid=commonFun.checkedAge(this.value);
            if(!ageValid) {
                globalFun.addClass(this,'error');
                showErrorAfter && createElement(this,errorMsg);
                return errorMsg;
            }
            else {
                globalFun.removeClass(this,'error');
                globalFun.addClass(this,'valid');
                showErrorAfter && removeElement(this);
            }
        }
    },
    isCardExist:function(errorMsg,showErrorAfter) {
        var getResult='',
            that=this;
        if(this.value.length!=18) {
            return;
        }
        commonFun.useAjax({
            type:'GET',
            async: false,
            url:'/authentication/identityNumber/'+this.value+'/is-exist'
        },function(response) {
            if(response.data.status) {
                // 身份证已存在
                getResult=errorMsg;
                globalFun.addClass(that,'error');
                showErrorAfter && createElement(this,errorMsg);
            }
            else {
                getResult='';
                globalFun.removeClass(that,'error');
                globalFun.addClass(that,'valid');
                showErrorAfter && removeElement(this);
            }
        });
        return getResult;
    },
    isMobileExist:function(errorMsg,showErrorAfter) {
        var getResult='',
            that=this;
        commonFun.useAjax({
            type:'GET',
            async: false,
            url:'/register/user/mobile/'+this.value+'/is-exist'
        },function(response) {
            if(response.data.status) {
                // 如果为true说明手机已存在或已注册
                getResult=errorMsg;
                globalFun.addClass(that,'error');
                showErrorAfter && createElement(that,errorMsg);
            }
            else {
                getResult='';
                globalFun.removeClass(that,'error');
                globalFun.addClass(that,'valid');
                showErrorAfter && removeElement(that);
            }
        });
        return getResult;
    },
    isMobileRetrieveExist:function(errorMsg,showErrorAfter) {
        var getResult='',
            that=this;
        commonFun.useAjax({
            type:'GET',
            async: false,
            url:'/mobile-retrieve-password/mobile/'+this.value+'/is-exist?random=' + new Date().getTime()
        },function(response) {
            if(response.data.status) {
                // 如果为true说明手机已存在
                getResult='';
                globalFun.removeClass(that,'error');
                globalFun.addClass(that,'valid');
                showErrorAfter && removeElement(that);
            }
            else {
                getResult=errorMsg;
                globalFun.addClass(that,'error');
                showErrorAfter && createElement(that,errorMsg);
            }
        });
        return getResult;
    },

    //推荐人是非存在
    isReferrerExist:function(errorMsg,showErrorAfter) {
        var getResult='',
            that=this;
        //只验证推荐人是否存在，不验证是否为空
        if(this.value=='') {
            getResult='';
            globalFun.removeClass(that,'error');
            showErrorAfter && removeElement(that);
            return '';
        }
        commonFun.useAjax({
            type:'GET',
            async: false,
            url:'/register/user/referrer/'+this.value+'/is-exist'
        },function(response) {
            if(response.data.status) {
                // 如果为true说明推荐人存在
                getResult='';
                globalFun.removeClass(that,'error');
                showErrorAfter && removeElement(that);
            }
            else {
                getResult=errorMsg;
                globalFun.addClass(that,'error');
                showErrorAfter && createElement(that,errorMsg);
            }
        });
        return getResult;
    }

};
// *****Validator验证类*******

function ValidatorForm(cache,checkOption) {
     this.cache = [];
     this.checkOption = {};
     this.newStrategy=function(name,callback) {
         strategies[name]=function(data) {
             callback && callback(data);
         }
     }
     this.add=function(dom, rules,errorAfter) {
         var self = this;
         self.checkOption[dom.name]=[];
         for (var i = 0, rule; rule = rules[i++];) {
             self.checkOption[dom.name].push(rule);
         }

         self.cache.push(function(thisDom) {
             var domName=thisDom.name;
             var domOption=self.checkOption[domName];
             if(!domOption) {
                return;
             }
             var len=domOption.length,
                 getErrorMsg;

             for(var j=0;j<len;j++) {
                 var strategy=domOption[j].strategy.split(':').shift();
                 var errorMsg=domOption[j].errorMsg;
                 var optionParams=[];
                 optionParams.push(errorMsg);
                 var secondParam=domOption[j].strategy.split(':')[1];

                 secondParam && optionParams.push(secondParam);
                 errorAfter && optionParams.push(errorAfter);
                 getErrorMsg=strategies[strategy].apply(thisDom,optionParams);

                 if(getErrorMsg) {
                     break;//跳出for循环
                 }
             }
             return getErrorMsg;
         });
     }
     this.start=function(dom) {
         var validatorFunc = this.cache[0];
         var errorMsg=validatorFunc(dom);
         if (errorMsg) {
             return errorMsg;
         }
     }
}

module.exports=ValidatorForm;


