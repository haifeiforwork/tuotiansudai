<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.full_screen}" pageJavascript="${js.index}" activeNav="首页" activeLeftNav="">
<div class="home-page-container">
    <div class="banner-box">
        <div class="banner-img-list">
            <a href="/activity/ranking" target="_blank">
                <img src="${staticServer}/images/sign/activities/ranking/qph.jpg" alt="抢排行，送大礼">
            </a>
            <a href="/activity/grand" target="_blank">
                <img src="${staticServer}/images/sign/activities/grand/banner-grand.jpg" alt="累计收益兑大奖">
            </a>
             <a href="/activity/recruit" target="_blank">
                 <img src="${staticServer}/images/banner/banner-home03.png" alt="招募代理">
             </a>
        </div>
        <ul class="scroll-num">
            <li class="selected"></li>
            <li></li>
            <li></li>
        </ul>

        <div class="page-width">
            <@global.isAnonymous>
            <div class="register-ad-box fr tc">
                <p class="num-text clearfix">
                    <span class="percent">46</span>
                    <span class="num-unit">倍</span>
                </p>
                <b class="h-title">活期存款收益</b>
                <p class="welcome-text">
                    <img src="${staticServer}/images/sign/welcome-text.png" width="100%" alt="welcome">
                </p>
                <a class="btn-normal" href="/register/user">免费注册 </a>
                <i class="clearfix tr">已有账户？<a href="/login"> 立即登录</a></i>
            </div>
            </@global.isAnonymous>
        </div>
    </div>
    <div class="notice-list">
        <div class="notice-container">
            <h3>最新公告</h3>
            <div class="notice-text">
                <span class="text-title">拓天速贷12月最新投资排名，12月标王揭晓！</span>
                <span class="text-date">2016-01-01</span>
                <a href="#" class="text-href">查看更多</a>
            </div>
        </div>
    </div>
    <div class="main-advantage page-width">
        <dl>
            <dd>
                <a href="/about/assurance" target="_blank">
                <img class="icon-off" src="${staticServer}/images/icons/icon-off-1.png" alt="超高收益 最低门槛" >
                <img class="icon-on" src="${staticServer}/images/icons/icon-on-1.png" alt="超高收益 最低门槛" >
                <span class="clearfix">
                     <b class="clearfix">超高收益 最低门槛</b>
                    最高46倍活期存款收益，最低投资门槛50元
                </span>
                </a>
            </dd>
            <dd>
                <a href="/about/assurance" target="_blank">
                <img class="icon-off" src="${staticServer}/images/icons/icon-off-2.png" alt="三方监管 放心理财">
                <img class="icon-on" src="${staticServer}/images/icons/icon-on-2.png" alt="三方监管 放心理财" >
                <span class="clearfix">
                    <b class="clearfix">三方监管 放心理财</b>
                    第三方资金监管，第三方支付
                </span>
                    </a>
            </dd>
            <dd>
                <a href="/about/assurance" target="_blank">
                <img class="icon-off" src="${staticServer}/images/icons/icon-off-3.png" alt="实力雄厚 安全保障">
                <img class="icon-on" src="${staticServer}/images/icons/icon-on-3.png" alt="实力雄厚 安全保障" >
                <span class="clearfix">
                    <b class="clearfix">实力雄厚 安全保障</b>
                    上市企业投资，资金数据均安全
                </span>
                    </a>
            </dd>
        </dl>
    </div>
    <div class="home-content" id="productFrame">
        <div class="page-width clearfix">
            <h3 class="label-title">
                <span class="product-icon">
                    产品介绍
                </span>
                <span class="notice-icon">
                    拓天上市
                </span>
            </h3>
            <div class="product-list">
                <ul>
                    <li class="syl-text">
                        <a href="#">
                            <div class="icon-proimg">
                                <img class="icon-off" src="${staticServer}/images/icons/syl-icon.png" alt="速盈利 快速高效">
                            </div>
                            <div class="product-intro">
                                <span class="text-intro">快速高效，银行活期28倍</span>
                            </div>
                            <div class="product-income">
                                <p class="income-num-text">
                                    <span class="income-num">10</span>
                                    <span>%</span>
                                </p>
                                <p class="income-text-intro">
                                    年化收益
                                </p>
                            </div>
                        </a>
                    </li>
                    <li class="wyx-text">
                        <a href="#">
                            <div class="icon-proimg">
                                <img class="icon-off" src="${staticServer}/images/icons/wyx-icon.png" alt="稳盈绣 稳健灵活">
                            </div>
                            <div class="product-intro">
                                <span class="text-intro">稳健灵活，收益高，银行定期8倍以上</span>
                            </div>
                            <div class="product-income">
                                <p class="income-num-text">
                                    <span class="income-num">12</span>
                                    <span>%</span>
                                </p>
                                <p class="income-text-intro">
                                    年化收益
                                </p>
                            </div>
                        </a>
                    </li>
                    <li class="jyf-text li-last">
                        <a href="#">
                            <div class="icon-proimg">
                                <img class="icon-off" src="${staticServer}/images/icons/jyf-icon.png" alt="久盈富 财富法宝">
                            </div>
                            <div class="product-intro">
                                <span class="text-intro">财富增长法宝，银行定期9倍以上，跑过GDP</span>
                            </div>
                            <div class="product-income">
                                <p class="income-num-text">
                                    <span class="income-num">14</span>
                                    <span>%</span>
                                </p>
                                <p class="income-text-intro">
                                    年化收益
                                </p>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="company-up">
                <a href="http://www.iqiyi.com/w_19rthksnrd.html#vfrm=2-3-0-1" target="_blank">
                    <img src="${staticServer}/images/banner/video-images.png" alt="拓天伟业挂牌视频">
                </a>
            </div>
        </div>
        <div class="page-width clearfix">
            <h3 class="label-title">
                <span class="hot-product">
                    热门产品
                </span>
                <a href="/loan-list" class="hot-more">更多>></a>
            </h3>
            <div class="product-box-list fl">
                <div class="product-box-inner">

                    <div class="product-box tc product-type">
                        <i class="img-syl"></i>
                        <div class="pad-m" >
                            <h2 class="pr-title">速盈利</h2>
                            <div class="pr-square tc">
                                <div class="pr-square-in">
                                    <em><b>23</b>%</em>
                                    <i>年化收益</i>
                                </div>
                            </div>
                            <dl class="pr-info">
                                <dd class="dl-month"><i>2</i>tian <span>项目期限</span></dd>
                                <dd class="dl-amount"><i>234元</i><span>项目金额</span></dd>
                            </dl>
                            <div class="project-schedule clear-blank clearfix">
                                <div class="p-title">
                                    <span class="fl">项目进度</span>
                                    <span class="point fr">34%</span>
                                </div>
                                <div class="process-percent">
                                    <div class="percent" style="width:20%"></div>
                                </div>
                            </div>
                        </div>
                        <a href="#" class="btn-normal">立即投资</a>
                    </div>

                    <div class="product-box tc product-type">
                        <i class="img-wyx"></i>
                        <div class="pad-m" >
                            <h2 class="pr-title">稳盈绣</h2>
                            <div class="pr-square tc">
                                <div class="pr-square-in">
                                    <em><b>23</b>%</em>
                                    <i>年化收益</i>
                                </div>
                            </div>
                            <dl class="pr-info">
                                <dd class="dl-month"><i>2</i>tian <span>项目期限</span></dd>
                                <dd class="dl-amount"><i>234元</i><span>项目金额</span></dd>
                            </dl>
                            <div class="project-schedule clear-blank clearfix">
                                <div class="p-title">
                                    <span class="fl">项目进度</span>
                                    <span class="point fr">34%</span>
                                </div>
                                <div class="process-percent">
                                    <div class="percent" style="width:20%"></div>
                                </div>
                            </div>
                        </div>
                        <a href="#" class="btn-normal">立即投资</a>
                    </div>

                    <div class="product-box tc product-type">
                        <i class="img-jyf"></i>
                        <div class="pad-m" >
                            <h2 class="pr-title">久盈富</h2>
                            <div class="pr-square tc">
                                <div class="pr-square-in">
                                    <em><b>23</b>%</em>
                                    <i>年化收益</i>
                                </div>
                            </div>
                            <dl class="pr-info">
                                <dd class="dl-month"><i>2</i>tian <span>项目期限</span></dd>
                                <dd class="dl-amount"><i>234元</i><span>项目金额</span></dd>
                            </dl>
                            <div class="project-schedule clear-blank clearfix">
                                <div class="p-title">
                                    <span class="fl">项目进度</span>
                                    <span class="point fr">34%</span>
                                </div>
                                <div class="process-percent">
                                    <div class="percent" style="width:20%"></div>
                                </div>
                            </div>
                        </div>
                        <a href="#" class="btn-normal">立即投资</a>
                    </div>

                    <#list loans as loan>
                    <div class="product-box tc <#if loan.activityType=="NEWBIE">new-standard</#if>">
                        <#if loan.activityType=='NEWBIE'><i class="hot-new"></i></#if>
                        <div class="pad-m" title="${loan.name}" data-url="/loan/${loan.id?string.computer}">
                            <h2 class="pr-title">${loan.name}</h2>
                            <div class="pr-square tc">
                                <div class="pr-square-in">
                                    <em><b><@percentInteger>${loan.baseRate}</@percentInteger></b><@percentFraction>${loan.baseRate}</@percentFraction>
                                        <#if (loan.activityRate > 0) >+<@percentInteger>${loan.activityRate}</@percentInteger>
                                            <@percentFraction>${loan.activityRate}</@percentFraction></#if>%</em>
                                    <i>年化收益</i>
                                </div>
                            </div>
                            <dl class="pr-info">
                                <dd class="dl-month"><i>${loan.periods}</i>${loan.isPeriodMonthUnit?string("个月", "天")} <span>项目期限</span></dd>
                                <dd class="dl-amount"><i><@amount>${loan.amount}</@amount>元</i><span>项目金额</span></dd>
                            </dl>
                            <div class="project-schedule clear-blank clearfix">
                                <div class="p-title">
                                    <span class="fl">项目进度</span>
                                    <span class="point fr">${loan.progress}%</span>
                                </div>
                                <div class="process-percent">
                                    <div class="percent" style="width:${loan.progress}%"></div>
                                </div>
                            </div>
                        </div>
                        <#if loan.status=="RAISING">
                        <a href="/loan/${loan.id?string.computer}" class="btn-normal">立即投资</a>
                        <#elseif loan.status=="PREHEAT">
                        <a href="/loan/${loan.id?string.computer}" class="btn-normal wait-invest">预热中</a>
                        <#else>
                        <button type="button" disabled class="btn-normal">已售罄</button>
                        </#if>
                    </div>
                    </#list>
                </div>
                
            </div>
        </div>
        <div class="page-width clearfix">
            <h3 class="label-title">
                <span class="report-icon">
                    运营报告
                </span>
            </h3>
            <div class="clear-blank invest-total">
                <span class="fl">累计投资人数: 
                    <strong data-count="${(userCount?string('0'))!}" id="userCount"><i>${(userCount?string('0'))!}</i></strong>人
                </span>
            </div>
        </div>

    </div>
</div>
</@global.main>