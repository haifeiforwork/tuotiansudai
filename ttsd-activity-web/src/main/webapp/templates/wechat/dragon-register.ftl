<#import "wechat-global.ftl" as global>

<@global.main pageCss="${css.dragon_register}" pageJavascript="${js.dragon_register}"  title="助力好友抢红包" >

<div class="wechat-invite-container" id="wechatInvite">
    <div class="register-form-container">
        <h3 class="title-item">注册完成后现金红包会直接放到您的账户中哦~</h3>
        <form action="/activity/wechat/dragon/register" method="post" class="register-item" id="registerForm">
            <div class="model-item">
                <input type="text" name="mobile" value="" tabindex="1"  class="int-item" maxlength="11" placeholder="请输入您的手机号" validate/>
            </div>

            <div class="model-item">
                <input type="password" name="password"  value="" tabindex="2"  class="int-item" maxlength="20" placeholder="请输入您的密码" validate/>
            </div>

            <div class="model-item">
                <input type="text" name="appCaptcha" id="appCaptcha" value="" tabindex="3"  class="int-item" maxlength="6" placeholder="请输入短信验证码" validate/>
                <input type="button" class="ignore get-code" value="获取验证码" id="getCaptchaBtn" disabled>
            </div>
            
            <div class="model-item text-model">
                <a  href="/activity/wechat/dragon/toLogin?sharer=${sharer}&shareUniqueCode=${shareUniqueCode}" class="fr login-link ">登录</a>
            </div>
            <div class="model-item text-model">
                <i class="icon-check active"></i>
                <span class="agree-item">同意拓天速贷<strong class="agree-text">《服务协议》</strong></span>
                <input type="hidden" name="agreement" class="agree-check" value="true" validate id="agreementRegister"/>
                <input type="hidden" name="source" value="WEB">
                <input type="hidden" name="referrer" value="loginName">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="redirectToAfterRegisterSuccess" value="/activity/wechat/dragon/fetchCoupon">
            </div>

            <div class="model-item text-model tc">
                <input type="submit" value="注册领取" class="register-btn" id="registerSubmit"/>
            </div>
        </form>
    </div>
</div>
<#include '../module/register-agreement.ftl' />
</@global.main>