<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.risk_estimate}" pageJavascript="${js.risk_estimate}" activeNav="我的账户" activeLeftNav="个人资料" title="投资偏好评估结果">
<div class="content-container invetsment-preferences" id="investmentBox">
    <h4 class="column-title"><em class="tc">投资偏好评估</em></h4>
    <div class="result-item">
        <div class="title-text">您的投资偏好为</div>
        <div class="result-text">${estimate.getType()}</div>
        <div class="intro-text">
            ${estimate.getDescription()}
        </div>
        <div class="link-item">
            <a href="/risk-estimate?retry=true" class="btn-item to-risk">重新评估</a>
            <a href="/loan-list" class="btn-item">立即投资</a>
        </div>
    </div>
</div>
</@global.main>


