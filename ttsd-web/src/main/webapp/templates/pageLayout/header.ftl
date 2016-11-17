<div class="header-container">
    <div class="header-download">
        <div id="closeDownloadBox" class="icon-close img-close-tip" ></div>
        <div class="img-logo-tip" ></div>
        <span>APP客户端重磅来袭<br/>更便捷更安全</span>
        <a href="#" class="btn-normal fr" id="btnExperience">立即体验</a>
    </div>
    <div class="header-top page-width">
        <span class="fl service-time">客服电话：400-169-1188<time>（服务时间：9:00－20:00）</time></span>
        <ul class="fr">
            <li class="membership-store">
                <a href="/point-shop" target="_blank">积分商城</a>
            </li>
            <li class="header-membership">
                <a href="/membership" target="_blank">会员中心</a>
            </li>
            <li class="header-activity-center">
                <a href="${webServer}/activity/activity-center">活动中心</a>
            </li>
            <li class="header-help-center">
                <a href="/help/help-center">帮助中心</a>
            </li>
        <@global.isNotAnonymous>
            <li class="header-login">
                <a class="personal-info-link" href="${requestContext.getContextPath()}/personal-info"><@global.security.authentication property="principal.mobile" /></a>
            </li>
            <li class="header-register"><a id="logout-link" href="javascript:void(0);" class="logout">退出</a>
                <form id="logout-form" class="logout-form" action="/logout" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </li>
            <li class="header-message">
                <a href="/message/user-messages">消息
                    <span class="message-badge <@unreadMessageCount><@global.security.authentication property="principal.mobile"/></@unreadMessageCount>">
                        <@unreadMessageCount><@global.security.authentication property="principal.mobile"/></@unreadMessageCount>
                    </span>
                </a>
            </li>
        </@global.isNotAnonymous>

        <@global.isAnonymous>
            <li class="header-login">
                <a href="/login" onclick="cnzzPush.trackClick('14顶部导航','登录')">登录</a>
            </li>
            <li class="header-register">
                <a href="/register/user<#if channel??>?channel=${channel}</#if>" onclick="cnzzPush.trackClick('15顶部导航','注册')">注册</a>
            </li>
        </@global.isAnonymous>
        </ul>

    </div>
</div>

<#if Session.impersonate?? && Session.impersonate == '1'>
<div style="position: fixed; right: 10px; top: 20px; font-size: 35px; color: red; z-index: 10">模拟登录</div>
<div style="position: fixed; left: 10px; top: 20px; font-size: 35px; color: green; z-index: 10">模拟登录</div>
</#if>

