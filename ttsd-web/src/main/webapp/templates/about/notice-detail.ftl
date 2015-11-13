<#import "../macro/global.ftl" as global>
    <@global.main pageCss="${css.about_us}" pageJavascript="" activeNav="关于我们" activeLeftNav="拓天公告" title="拓天公告详情">
    <div class="about-us-container" id="noticeDetail">
        <div class="crumb-lead">
            首页 > 关于我们 > 拓天公告 >
        </div>
        <h2 class="a-title article">
            <span class="title">${announcement.title}</span>
            <time class="tr">发表时间：<i>${(announcement.createdTime?string("yyyy-MM-dd"))!}</i></time>


        </h2>
        <div class="detail-content">
            ${announcement.content}
        </div>

    <footer class="fr">
        拓天速贷客服中心
        <br/>
        <span class="update-time"> ${(announcement.updateTime?string("yyyy年MM月dd日"))!}</span></footer>
    </div>

</@global.main>