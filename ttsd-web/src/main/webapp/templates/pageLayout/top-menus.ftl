<div class="nav-container">
    <div class="img-top page-width clearfix">
        <div class="logo">
            <a href="${applicationContext}/" class="logo-bg"></a>
        </div>
        <div class="login-pop-app" id="iphone-app-pop">
            <em></em>
            <a href="javascript:" class="text" onclick="cnzzPush.trackClick('13顶部导航','手机APP')"><i>手机APP</i>
                投资更便利</a>
            <div id="iphone-app-img" class="img-app-pc-top hide"></div>
        </div>
        <i class="fa fa-navicon show-main-menu fr" id="showMainMenu"></i>
    </div>

<#if activeNav??>

    <ul id="TopMainMenuList" class="nav-menu page-width clearfix" >
        <#list mainMenus as menu>
            <#if menu.navigation?? && menu.navigation="true">
                <li <#if menu.title==activeNav>class="active"</#if>><a  href="${menu.url}"
                       onclick="cnzzPush.trackClick('${menu.category}','${menu.title}')" >${menu.title}</a></li>
            </#if>
        </#list>
        <li class="top-membership"><a href="/membership">会员中心</a> </li>
        <li class="top-activity">
            <a href="/activity-center">活动中心</a>
        </li>
    </ul>
</#if>
</div>