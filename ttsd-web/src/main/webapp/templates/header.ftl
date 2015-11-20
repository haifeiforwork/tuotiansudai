<div class="header-container">
    <div class="header page-width">
            <span class="fl">客服电话:400-169-1188<time>服务时间:(9:00－21:00)</time></span>
            <ul class="fr">
                <li><a href="javascript:">手机APP</a></li>
                <@global.isNotAnonymous>
                <li><a href="${requestContext.getContextPath()}/personal-info"><@global.security.authentication property="principal.username" /></a></li>
                <li><a id="logout-link" href="/logout" class="logout">退出</a>
                    <form id="logout-form" class="logout-form" action="/logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </li>
                </@global.isNotAnonymous>

                <@global.isAnonymous>
                <li><a href="${requestContext.getContextPath()}/login">登录</a></li>
                <li><a href="${requestContext.getContextPath()}/register/user">注册</a></li>

                </@global.isAnonymous>
            </ul>

    </div>
</div>
