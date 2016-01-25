<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.my_account}" pageJavascript="${js.my_treasure}" activeNav="我的账户" activeLeftNav="我的宝藏" title="我的宝藏">

<div class="content-container my-treasure-content">
    <h4 class="column-title">
        <em class="tc title-navli active">体验券</em>
        <em class="tc title-navli">加息券</em>
        <em class="tc title-navli">使用记录</em>
    </h4>

    <#-- 体验券 start -->
    <div class="list-tab tab-show">
        <#list moneyCoupons as coupon>
        <div class="experience-ticket-box ${coupon.used?string('ticket-status-used', '')} ${coupon.expired?string('ticket-status-expired', '')}">
            <div class="vertical-line"></div>
            <div class="ticket-amount fl tc">
                <h3>${coupon.name}</h3>
                <strong>${(coupon.amount/100)?string("0")}元</strong>
                <time>有效期：在${coupon.endTime?date}前使用</time>
            </div>
            <div class="ticket-info fr">
                <dl class="pad-s">
                    <dt>使用条件：</dt>
                    <dd>
                        <#list coupon.productTypeList as productType>
                            <img src="/images/icons/${productType}.png" alt="投资体验券">
                        </#list>
                        产品线可用
                    </dd>
                    <dd>
                        投资满<@amount>${coupon.investLowerLimit?string(0)}</@amount>元可用。
                    </dd>
                    <dd class="tc"><a href="${coupon.unused?string('/loan-list','javascript:void(0);')}" class="btn-action">立即使用</a></dd>
                </dl>
            </div>
            <div class="sign-seal sign-used"><span>已<br/>使用</span></div>
            <div class="sign-seal sign-expired"><span>已<br/>过期</span></div>
        </div>
        <#else>
        <p class="no-treasure-tip tc pad-m">您当前没有宝藏，敬请期待！</p>
        </#list>

        <div class="ticket-use-help clear-blank-m">
            <b>体验券使用规则：</b>
            <p>
                1.  体验券仅适用于标的投资；<br/>
                2.  体验结束后系统自动收回本金，收益转回用户账户内，详见“我的账户”-->“资金管理”；<br/>
                3.  如体验券中有限制条件， 用户必须按照限制条件使用。<br/>
            </p>
        </div>
    </div>
    <#-- 体验券 end -->

    <#-- 加息券 start -->
    <div class="list-tab">
        <#list interestCoupons as coupon>
            <div class="experience-ticket-box ${coupon.used?string('ticket-status-used', '')} ${coupon.expired?string('ticket-status-expired', '')}">
                <div class="vertical-line"></div>
                <div class="ticket-amount fl tc">
                    <h3>${coupon.name}</h3>
                    <strong>${coupon.rate*100?float}%<em>年化利率</em></strong>
                    <time>有效期：在${coupon.endTime?date}前使用</time>
                </div>
                <div class="ticket-info fr">
                    <dl class="pad-s">
                        <dt>使用条件：</dt>
                        <dd>
                            <#list coupon.productTypeList as productType>
                                <img src="/images/icons/${productType}.png" alt="投资体验券">
                            </#list>
                            产品线可用
                        </dd>
                        <dd>
                            投资<@amount>${coupon.investUpperLimit?string(0)}</@amount>元以内可用。
                        </dd>
                        <dd class="tc"><a href="${coupon.unused?string('/loan-list','javascript:void(0);')}" class="btn-action">立即使用</a></dd>
                    </dl>
                </div>
                <div class="sign-seal sign-used"><span>已<br/>使用</span></div>
                <div class="sign-seal sign-expired"><span>已<br/>过期</span></div>
            </div>
        <#else>
            <p class="no-treasure-tip tc pad-m">您当前没有宝藏，敬请期待！</p>
        </#list>

        <div class="ticket-use-help clear-blank-m">
            <b>加息券使用规则：</b>
            <p>
                1.  加息券仅适用于标的投资；<br/>
                2.  标的回款后，加息券所得收益转回用户账户内，详见“我的账户”-->“资金管理”；<br/>
                3.  如加息券中有限制条件， 用户必须按照限制条件使用；<br/>
                4.  使用方式：在“我要投资”-->“标的详情”页面选择使用加息券。<br/>
            </p>
        </div>
    </div>
    <#-- 加息券 end -->

    <#-- 使用记录 start -->
    <div class="list-tab invest-list-content">
        <div class="item-block status-filter">
            <span class="sub-hd">活动券类型:</span>
            <span class="select-item current" data-status="touzi">体验券</span>
            <span class="select-item" data-status="jiaxi">加息券</span>
        </div>
        <div class="clear-blank"></div>
        <div class="record-tab tab-show">
            <div class="invest-list"></div>
            <div id="use-record-money" class="pagination" data-url="/coupon/use-record" data-page-size="10">
            </div>
        </div>

        <div class="record-tab">
            <div class="invest-list-interest"></div>
            <div id="use-record-interest" class="pagination" data-url="/coupon/use-record" data-page-size="10">
            </div>
        </div>
    </div>
    <#-- 使用记录 end -->
</div>
</@global.main>