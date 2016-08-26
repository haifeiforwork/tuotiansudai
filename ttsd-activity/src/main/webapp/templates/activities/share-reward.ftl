<#import "../macro/global.ftl" as global>
<@global.main pageCss="${css.company_activity}" pageJavascript="${js.share_reward}" activeNav="" activeLeftNav="" title="推荐奖励_拓天速贷" keywords="拓天速贷,推荐奖励,P2P理财,短期理财,短期投资,拓天速贷2级推荐机制" description="拓天速贷针对老用户推出2级推荐机制的推荐奖励,可以让您的财富快速升值.">
<div class="share-reward-container">
    <div class="rank-phone-model">
        <img src="${staticServer}/activity/images/sign/actor/sharereward/share-top-bgg.png" width="100%">
    </div>
    <div class="wp clearfix">
        <div class="left-bg"></div>
        <div class="right-bg"></div>
        <div class="reward-info-title">
            <div class="title-text">
                天天带领亲朋好友富贵同享，一见钟情月月领佣金。
            </div>
            <p>
                平台用户邀请好友投资即可享受1%的年化投资额佣金；
                好友继续推荐投资用户，仍可享受1%的年化投资额佣金
                折合2%的收益率，多多推荐赢取丰厚奖金...&nbsp;...
            </p>
        </div>
        <div class="share-example">
            <div class="example-title">
                <span class="title-mole"></span>
                <span class="title-text"></span>
            </div>
            <p>A邀请B注册，B买入<span>10</span>万元180天产品，A获得<span>493.15</span>元现金奖励；</p>

            <p>B邀请C注册，C买入<span>1</span>万元90天产品，B获得<span>24.65</span>元现金奖励，A也获得<span>24.65</span>元现金奖励；</p>

            <div class="example-case">如果每人可以邀请3个朋友投资：</div>
            <div class="example-case-detail"></div>
        </div>
        <div class="share-recommend">
            <h1>PC</h1>
            <#if noAccount?? && noAccount>
                <h1>BUTTON1</h1>
                <a href="/register/account">立即推荐</a>
            <#else>
                <h1>BUTTON2</h1>
                <a href="/referrer/refer-list">立即推荐</a>
            </#if>
        </div>
        <div class="share-rules">
            <div class="rules-title">
                活动规则
            </div>
            <ul class="rules-list">
                <li>平台用户完整认证后享受2级推荐奖励；</li>
                <li>推荐好友成功投资享有1%的年化投资额佣金奖励；</li>
                <li>推荐奖励结算不限买入金额，不限购买笔数；</li>
                <li>佣金奖励在放款后一次性以现金形式直接发放；</li>
                <li>用户可在&nbsp;“我的账户”&nbsp;中查询。</li>
            </ul>
            <p class="rules-sign">***活动遵守拓天速贷法律声明，最终解释权归拓天速贷平台所有。</p>
        </div>
    </div>
</div>
<div class="share-reward-phone">
    <div class="rank-phone-model">
        <img src="${staticServer}/activity/images/sign/actor/sharereward/share-phone-top.png" width="100%">
    </div>
    <div class="wp clearfix">
        <div class="reward-info-title"></div>
        <div class="share-example"></div>
        <div class="share-recommend">
            <h1>PHONE</h1>
            <#if isAppSource?? && !isAppSource>
                <h1>NOT APP</h1>
                <#if noAccount?? && noAccount>
                    <h1>BUTTON3</h1>
                    <a href="/register/account">立即推荐</a>
                <#else>
                    <h1>BUTTON4</h1>
                    <a href="/referrer/refer-list">立即推荐</a>
                </#if>
            <#else>
                <h1>IS APP</h1>
                <#if isLogin?? && !isLogin>
                    <h1>BUTTON5</h1>
                    <a href="/login?redirect=/referrer/refer-list">立即推荐</a>
                <#else>
                    <h1>APP LOGIN</h1>
                    <#if noAccount?? && noAccount>
                        <h1>BUTTON6</h1>
                        <a href="/registeraccount">立即推荐</a>
                    <#else>
                        <h1>BUTTON7</h1>
                        <a href="/referrer/refer-list"
                           onclick="cnzzPush.trackClick('201APP分享','推荐奖励落地页','立即推荐')">立即推荐</a>
                    </#if>
                </#if>
            </#if>
        </div>
        <div class="share-rules"></div>
    </div>
</div>
</@global.main>