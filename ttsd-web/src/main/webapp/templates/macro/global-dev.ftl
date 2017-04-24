<#macro main pageCss pageJavascript="" activeNav="" activeLeftNav="" title="拓天速贷" keywords="" description="" site='main'>
    <#assign staticServer = "http://localhost:3008/">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>${title}</title>
    <meta name="keywords" content="${keywords}">
    <meta name="description" content="${description}">
    <#if responsive??>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    </#if>
    <meta name="_csrf" content="f60ab1bf-cb28-4ea9-9023-2ac992109c0c"/>
    <meta name="_csrf_header" content="X-CSRF-TOKEN"/>
    <meta name="360-site-verification" content="a3066008a453e5dfcd9f3e288862c9ef" />
    <meta name="sogou_site_verification" content="lXIPItRbXy"/>
    <meta name="baidu-site-verification" content="XVFtcOmhlc" />
    <link rel="stylesheet" type="text/css" href="http://localhost:3008/public/globalFun_page.css" charset="utf-8"/>
    <#if pageCss?? && pageCss != "">
        <link rel="stylesheet" type="text/css" href="${pageCss}" charset="utf-8"/>
    </#if>
</head>
<body>
    <#include "../pageLayout/header.ftl"/>
<div class="nav-container">
    <div class="img-top page-width clearfix">
        <div class="logo">
            <a href="/" class="logo-bg"></a>
        </div>
        <div class="login-pop-app" id="iphone-app-pop">
            <em class="img-app-download"></em>
            <a href="javascript:" class="text"><i>手机APP</i>
                投资更便利</a>
            <div id="iphone-app-img" class="img-app-pc-top hide"></div>
        </div>
        <i class="fa fa-navicon show-main-menu fr" id="showMainMenu"></i>
    </div>

    <ul id="TopMainMenuList" class="nav-menu page-width clearfix">
        <li>
            <a href="/">
                首页
            </a>
        </li>
        <li>
            <a href="/loan-list">
                我要投资
                <span class="icon-has-submenu"></span>
            </a>

            <ul class="sub-menu-list">
                <li><a href="/loan-list"> <i>●</i> 直投项目</a>
                </li>
                <li><a href="/transfer-list"> <i>●</i> 转让项目</a>
                </li>
            </ul>

        </li>
        <li>
            <a href="/loan-application">
                我要借款
            </a>
        </li>
        <li>
            <a href="/account">
                我的账户
            </a>
            <ul class="sub-menu-list">
            </ul>
        </li>
        <li>
            <a href="http://localhost:8050/?group=HOT&amp;index=1">
                拓天问答
            </a>
        </li>
        <li>
            <a href="/about/company">
                信息披露
                <span class="icon-has-submenu"></span>
            </a>

            <ul class="sub-menu-list">
                <li><a href="/about/company"> <i>●</i> 公司介绍</a>
                </li>
                <li><a href="/about/team"> <i>●</i> 团队介绍</a>
                </li>
                <li><a href="/about/notice"> <i>●</i> 拓天公告</a>
                </li>
                <li><a href="/about/media"> <i>●</i> 媒体报道</a>
                </li>
                <li><a href="/about/service-fee"> <i>●</i> 服务费用</a>
                </li>
                <li><a href="/about/contact"> <i>●</i> 联系我们</a>
                </li>
                <li><a href="/about/operational"> <i>●</i> 运营数据</a>
                </li>
            </ul>

        </li>
        <li class="top-membership"><a href="/membership">会员中心</a> </li>
        <li class="top-activity">
            <a href="http://localhost:8080/activity/activity-center">活动中心</a>
        </li>
    </ul>
</div>
<div class="main-frame full-screen clearfix">

    <#nested>
</div>
    <#include "../pageLayout/footer.ftl" />
<script>
    window.staticServer='http://localhost:3008';
</script>


<script src="http://localhost:3008/public/dllplugins/jquery.dll.js" defer></script>
<script src="http://localhost:3008/public/globalFun_page.js" defer></script>
<script src="${pageJavascript}" type="text/javascript" id="currentScript" defer></script>

</body>
</html>
</#macro>