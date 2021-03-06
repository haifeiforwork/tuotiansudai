<#import "../macro/global-dev.ftl" as global>
<#assign jsName = 'buy_free' >
<#assign js = {"${jsName}":"http://localhost:3008/wapSite/js/investment/${jsName}.js"} >
<#assign css = {"${jsName}":"http://localhost:3008/wapSite/js/investment/${jsName}.css"}>

<@global.main pageCss="${css.buy_free}" pageJavascript="${js.buy_free}" title="体验金购买成功">

<div class="my-account-content apply-transfer-success" >
    <div class="info">
        <i class="icon-success"></i>
        <em>投资成功</em>

        <ul class="input-list">
            <li>
                <label>投资金额</label>
                <span>1,000.00元</span>
            </li>
            <li>
                <label>所投项目</label>
                <span>房产抵押借款17060</span>
            </li>
            <li>
                <label>项目编号</label>
                <span>53413296907424</span>
            </li>
        </ul>
    </div>
</div>
<div class="button-note">
    <a href="#" class="btn-wap-normal next-step" >确定</a>
</div>

</@global.main>
