<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.index}" pageJavascript="${js.index}" activeNav="首页" activeLeftNav="" title="拓天速贷-互联网金融信息服务平台" keywords="拓天速贷,互联网金融平台,P2P投资,拓天借贷,网络投资" description="拓天速贷是基于互联网的金融信息服务平台,由拓天伟业(北京)资产管理有限公司旗下的拓天伟业(北京)金融信息服务有限公司运营.">

<div class="home-page-container" id="homePageContainer">
    <div class="banner-box">
        <div id="bannerBox" class="banner-box-inner compliance-index">
            <div class="invest-tip-wrap"><div class="invest-tip">市场有风险，出借需谨慎！</div></div>
            <ul class="banner-img-list">
                <#list bannerList as banner>
                    <li>
                        <a href="${banner.url}" target="_blank"
                           <#if banner.url == 'http://www.iqiyi.com/w_19rt7ygfmh.html#vfrm=8-8-0-1'>rel="nofollow"</#if>>
                            <img src="${commonStaticServer}${banner.webImageUrl}" data-app-img="${commonStaticServer}${banner.appImageUrl}" alt="${banner.title}">
                        </a>

                    </li>
                </#list>
            </ul>
        </div>
        <div class="page-width">
            <@global.isAnonymous>
                <div class="register-ad-box fr tc">
                    <b class="h-title">约定年化利率</b>
                    <p class="num-text clearfix">
                        <span class="percent">8%~10%</span>
                    </p>

                    <p class="welcome-text">welcome</p>
                    <a class="btn-normal" href="/register/user" >免费注册 </a>
                    <i class="clearfix tr">已有账户？<a href="/login"> 立即登录</a></i>
                </div>
            </@global.isAnonymous>
        </div>
    </div>

    <div class="notice-container bg-screen">
        <div class="page-width clearfix">
            <h3><i class="icon-notice"></i><span>最新公告</span></h3>
            <div class="notice-text scroll-top">
                <ul id="noticeList">
                    <#list announces as announce>
                        <#if announce.id == 187>
                            <#assign category = "70首页">
                            <#assign label = "计息调整">
                        <#elseif announce.id == 188>
                            <#assign category = "69首页">
                            <#assign label = "排行榜">
                        <#else>
                            <#assign category = "71首页">
                            <#assign label = "更多">
                        </#if>
                        <li class="clearfix">
                            <a href="/announce/${announce.id?string.computer}">
                                <span class="text-title fl">${announce.title}</span>
                                <span class="text-date fr">${announce.createdTime?string("yyyy-MM-dd")}</span>
                            </a>
                        </li>
                    </#list>
                </ul>
                <a href="/about/notice" class="text-href">更多></a>
            </div>
        </div>
    </div>
    <div class="main-advantage clearfix bg-screen">
        <div class="page-width">
            <dl>
                <dd>
                    <i class="guide sprite-homepage-icon-guide"></i>
                    <a href="/about/guide" target="_blank">
                        <b class="clearfix">稳健收益 较低门槛</b>
                        <span>约定年化利率8%~10%，<br>出借门槛50元起投</span>
                    </a>
                </dd>
                <dd>
                    <i class="risk sprite-homepage-icon-risk"></i>
                    <a href="/about/risk-flow" target="_blank">
                        <b class="clearfix">六重风控 审核严谨</b>
                        <span>22道审核手续，<br>项目安全透明无死角</span>
                    </a>
                </dd>
                <dd class="last">
                    <i class="assurance sprite-homepage-icon-assurce"></i>
                    <a href="/about/assurance" target="_blank">
                        <b class="clearfix">四大保障 出借无忧</b>
                        <span>12道保障措施并举，<br>资金、个人信息均安全</span>
                    </a>
                </dd>
            </dl>
        </div>
    </div>
    <div class="page-width">
        <div class="newer-exclusive-box clearfix">
            <div class="main-column-title">
                <i class="icon-title"></i>新手专享
            </div>

            <div class="newer-experience clearfix" data-url="/loan/1">
                <#--<i class="tag-icon"></i>-->
                <div class="con-inner">
                    <b class="newer-title">${experienceLoan.name} <span>限体验金购买</span></b>
                    <ul class="loan-info clearfix">
                        <li><span class="percent-number"> <i>${experienceLoan.baseRate}</i>%</span>约定年化利率</li>
                        <li><em class="duration-day">${experienceLoan.duration}</em>天<br>项目期限</li>
                    </ul>
                    <a href="/loan/1" class="btn-invest btn-normal">立即购买</a>
                </div>

            </div>
            <#if newbieLoan??>
                <div class="newer-experience clearfix hack-newbie" data-url="/loan/${newbieLoan.id?c}">
                    <i class="tag-icon sprite-homepage-icon-newuse"></i>
                    <div class="con-inner">
                        <b class="newer-title">${newbieLoan.name}</b>
                        <ul class="loan-info clearfix">
                            <li><span class="percent-number">
                                    <i><@percentInteger>${newbieLoan.baseRate+newbieLoan.activityRate}</@percentInteger></i>
                                <@percentFraction>${newbieLoan.baseRate+newbieLoan.activityRate}</@percentFraction>%
                                <#if (newbieLoan.newbieInterestCouponRate > 0)>
                                    <s class="sign-plus">+</s>
                                    <i><@percentInteger>${newbieLoan.newbieInterestCouponRate}</@percentInteger></i>
                                    <@percentFraction>${newbieLoan.newbieInterestCouponRate}</@percentFraction>%
                                </#if></span>约定年化利率
                            </li>
                            <li>最长<em class="duration-day">${newbieLoan.duration}</em>天<br>项目期限</li>
                        </ul>
                        <#if newbieLoan.status== 'RAISING'>
                        <#--筹款-->
                            <a href="javascript:void(0)" class="btn-invest btn-normal">立即购买</a>
                        </#if>
                        <#if newbieLoan.status== 'PREHEAT'>
                        <#--预热中-->
                            <a href="javascript:void(0)" class="btn-invest btn-normal preheat-btn btn-normal-zdy">
                                <#if newbieLoan.preheatSeconds lte 1800>

                                    <span class="preheat" data-time="${newbieLoan.preheatSeconds?string.computer}">
                                        <i class="minute_show"></i>分
                                        <i class="second_show"></i>秒后开标
                                    </span>
                                <#else>
                                ${(newbieLoan.fundraisingStartTime?string("yyyy-MM-dd HH时mm分"))!}放标
                                </#if>
                            </a>
                        </#if>
                        <#if ['RECHECK', 'REPAYING', 'OVERDUE', 'COMPLETE']?seq_contains(newbieLoan.status)>
                        <#--已售罄-->
                            <button class="btn-normal" disabled="">已售罄</button>
                        </#if>
                    </div>
                </div>
            </#if>
            <a href="/activity/landing-page" target="_blank" class="hot-bag"></a>
        </div>

        <#--优选债权-->
        <div class="main-column-title">
            <i class="icon-title"></i>优选债权
            <a href="/loan-list"  class="hot-more">更多></a>
        </div>

        <div class="normal-loan">
            <#include "component/loan-title.ftl">
            <#list normalLoans as loan>
            <#include "component/loan-row.ftl">
            </#list>
        </div>


        <#if enterpriseLoans??>
            <div class="main-column-title">
                <i class="icon-title"></i>经营性借款
                <a href="/loan-list" class="hot-more">更多></a>
            </div>

            <#include "component/loan-title.ftl">
            <#list enterpriseLoans as loan>
                <#include "component/loan-row.ftl">
            </#list>
        </#if>

        <div class="main-column-title">
            <i class="icon-title"></i>转让项目
            <a href="/transfer-list" class="hot-more">更多></a>
        </div>

        <div class="target-category-box clearfix bg-screen">
            <div class="transfer-title">
                <span>项目名称</span>
                <span>约定年化利率</span>
                <span>转让价格</span>
                <span>项目本金</span>
                <span>剩余天数</span>
                <span></span>
            </div>
        </div>

        <#list transferApplications as loan>
            <#include "component/transfer-row.ftl">
        </#list>

        <div class="media-coverage-box clearfix">
            <h3 class="label-title">
                媒体报道
                <a href="/about/media" class="hot-more">更多></a>
            </h3>
            <ul class="media-list">
                <li><i>●</i><a rel="nofollow" href="https://www.mika18.com/wdxw/ptxw/982.html" target="_blank">拓天伟业成建设银行“税易-助保贷”唯一运营商</a>
                    <time>2017-07-03</time>
                </li>
                <li><i>●</i><a rel="nofollow" href="https://baijiahao.baidu.com/s?id=1571613495782360&wfr=spider&for=pc"
                               target="_blank">保障体系再升级，拓天速贷牵手CFCA</a>
                    <time>2017-06-30</time>
                </li>
                <li><i>●</i><a rel="nofollow" href="https://baijiahao.baidu.com/s?id=1567754437495750&wfr=spider&for=pc" target="_blank">乘“风”破浪·拓天速贷傲立行业改革潮头</a>
                    <time>2016-05-19</time>
                </li>
                <li><i>●</i><a rel="nofollow" href="http://q.dahe.cn/2016/03-14/106571836.html" target="_blank">拓天速贷：财富盛宴大平台
                    感恩豪礼滚滚来</a>
                    <time>2016-03-14</time>
                </li>
                <li><i>●</i><a rel="nofollow" href="http://www.kaixian.tv/gd/2016/0201/994178.html" target="_blank">拓天速贷：新年贺岁嗨翻天 全民领取888元出借红包</a>
                    <time>2016-02-01</time>
                </li>
            </ul>
            <div class="media-logo-section sprite-homepage-media"></div>

        </div>

        <div class="partner-box clearfix">
            <h3 class="label-title">
                合作伙伴
            </h3>
            <ul class="partner-list">
                <li><a rel="nofollow" id="logo-zhhgj" href="http://www.wandouhuizu.com/" target="_blank"></a></li>
                <li><a rel="nofollow" class="logo-cfca sprite-homepage-logo-lian" href="http://www.umpay.com/" target="_blank"></a></li>
                <li><a rel="nofollow" class="logo-lian sprite-homepage-logo-cfca" href="https://www.anxinsign.com/" target="_blank"></a></li>
            </ul>
        </div>

    </div>
    <div class="book-invest-frame" style="display: none">
        <form name="bookInvest" class="book-invest-form" id="bookInvestForm">
            <div class="clearfix book-table-column">
                <div class="fl">选择项目</div>
                <div class="fr">
                    <table class="book-invest-table">
                        <tr>
                            <th></th>
                            <th>预约项目</th>
                            <th>约定年化利率</th>
                        </tr>
                        <tr>
                            <td class="tc">
                            <span class="init-radio-style">
                                <input type="radio" name="productType" id="po1" value="_90" class="radio-class">
                            </span>
                            </td>
                            <td class="product-type"><label for="po1">90天项目</label>
                            </td>
                            <td><label for="po1">11%</label></td>
                        </tr>
                        <tr>
                            <td>
                            <span class="init-radio-style">
                                <input type="radio" name="productType" id="po2" value="_180" class="radio-class">
                            </span>
                            </td>
                            <td class="product-type"><label for="po2">180天项目</label></td>
                            <td><label for="po2">12%</label></td>
                        </tr>
                        <tr>
                            <td>
                            <span class="init-radio-style">
                                <input type="radio" name="productType" id="po3" value="_360" class="radio-class">
                            </span>
                            </td>
                            <td class="product-type"><label for="po3">360天项目</label></td>
                            <td><label for="po3">13%</label></td>
                        </tr>
                    </table>
                </div>
            </div>
            <dl class="book-dl clearfix">
                <dt>预计投资金额</dt>
                <dd>
                <span>
                    <input type="text" class="form-text autoNumeric" name="bookingAmount" data-l-zero="deny"
                           data-v-min="0.00" data-v-max="99999999999.99" placeholder="0.00" class="autoNumeric"> 元
                </span>
                </dd>
            </dl>
            <div class="tc margin-top25">
                <button type="submit" class="btn btn-normal">确认预约</button>
            </div>
            <dl class="book-dl book-bottom-notice clearfix">
                <dt>预约说明</dt>
                <dd>1、每次预约仅能预约一个项目；<br/>
                    2、预约设置完成时开始进入预约队列；<br/>
                    3、预约后，当有相应标的时，我们会根据队列依次通知进行投资；<br/>
                    4、可同时添加多笔预约，每笔预约排名互相独立，互不影响；<br/>
                    5、每笔预约在通知投资后失效。<br/>

                </dd>
            </dl>
        </form>
    </div>
</div>


    <#include "component/coupon-alert.ftl" />
    <#include "component/red-envelope-float.ftl" />
    <#include "component/site-map.ftl" />
</@global.main>