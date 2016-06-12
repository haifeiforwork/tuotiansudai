<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.my_account}" pageJavascript="${js.experience_loan_detail}" activeNav="我要投资" activeLeftNav="" title="新手体验项目">
<div class="loan-detail-content" data-loan-progress="${loan.progress?string.computer}">
    <div class="borderBox clearfix no-border">
        <div class="loan-model bg-w borderBox">
            <div class="news-share fl">
                <h2 class="title hd new">${loan.name}<span class="new-free"></span></h2>
                <div class="chart-box">
                    <div class="box" title="已投${loan.progress?string("0.00")}%">
                        <div class="bg"></div>
                        <div class="rount"></div>
                        <div class="bg2"></div>
                        <div class="rount2" style="display: none;"></div>
                        <div class="pr-square-in">
                            <em>
                                <b>${loan.baseRate}%</b>
                            </em>
                            <i>预期年化收益</i>
                        </div>
                    </div>
                </div>
                <div class="chart-info">
                    项目金额：${loan.loanAmount}元<br/>
                    项目期限：${loan.duration}天<br/>
                    起息时间：即投即生息。<br/>
                    还款方式：到期付息,体验金收回。<br/>
                    投资要求：新手体验标仅能使用新手体验券进行投资。<br/>
                    项目简介：此项目为拓天速贷体验项目，是由拓天速贷设立的专门提供给新注册客户，进行投资体验的虚拟项目。<br/>
                </div>
                <div class="product-type-text">新手体验项目</div>
            </div>
            <div class="account-info fl">
                <h5 class="l-title">拓天速贷提醒您：投资非存款，投资需谨慎！</h5>
                <#if loan.loanStatus == 'RAISING'>
                    <form action="/experience-invest" method="post" id="investForm">
                        <dl class="account-list new-text">
                            <dd class="experience-info clearfix">
                                <span class="fl">可投金额：</span>
                                <em class="fr">
                                    <i class="amountNeedRaised-i" data-amount-need-raised="${loan.investAmount}">${loan.investAmount}</i> 元
                                </em>
                            </dd>

                            <input type="hidden" name="loanId" value="1"/>
                            <input type="hidden" name="userCouponIds" value="<#if coupon??>${coupon.id?string.computer}</#if>" data-coupon-id="<#if coupon??>${coupon.couponId?string.computer}</#if>"/>
                            <input type="hidden" name="amount" value="0"/>

                            <dd class="experience-ticket clearfix">
                                <span class="fl">优惠券：</span>
                                <div class="fr experience-ticket-box">
                                    <em class="experience-ticket-input <#if coupon??==false>disabled</#if>" id="use-experience-ticket">
                                        <span>
                                            <#if coupon??>
                                            ${coupon.name}<@amount>${coupon.amount?string.computer}</@amount>元
                                            <#else>
                                                无可用体验金
                                            </#if>
                                        </span>
                                    </em>
                                </div>
                            </dd>

                            <dd class="expected-interest-dd tc mb-20">
                                <span>预计总收益：</span>
                                <span class="principal-income">0.00</span> 元
                            </dd>

                            <dd class="mb-20">
                                <button id="investSubmit" class="btn-pay btn-normal" type="button"
                                        <#if coupon?? == false>disabled="disabled"</#if>>立即体验
                                </button>
                            </dd>
                        </dl>
                    </form>
                </#if>
                <#if ["REPAYING", "COMPLETE"]?seq_contains(loan.loanStatus)>
                    <form action="/loan-list" method="get">
                        <dl class="account-list">
                            <dd class="img-status">
                                <img src="${staticServer}/images/sign/loan/${loan.loanStatus?lower_case}.png" alt=""/>
                            </dd>
                            <dd>
                                <button class="btn-pay btn-normal" type="submit">查看其他项目</button>
                            </dd>
                        </dl>
                    </form>
                </#if>
            </div>
        </div>

        <div class="chart-info-responsive bg-w">
            项目金额：${loan.loanAmount}元<br/>
            项目期限：${loan.duration}天<br/>
            起息时间：即投即生息<br/>
                    还款方式：到期付息,体验金收回。<br/>
                    投资要求：新手体验标仅能使用新手体验券进行投资。<br/>
                    项目简介：此项目为拓天速贷体验项目，是由拓天速贷设立的专门提供给新注册客户，进行投资体验的虚拟项目。<br/>
        </div>
        <div class="bg-w borderBox mt-20 project-model">
            <div class="model-nav">
                <h3>项目描述</h3>
            </div>
            <div class="model-content">
                <ul class="info-list">
                    <li>1、新手体验项目是由拓天速贷专门提供给平台各类型新手客户体验平台流程的活动项目。</li>
                    <li>2、投资体验项目无需充值。</li>
                    <li>3、新手体验券是由拓天速贷用平台活动方式，为新注册用于提供平台项目投资体验的活动金额，新手体验券只能投资体验项目，不可提现，使用后可产生红包奖励</li>
                    <li>4、新注册用户通过获得体验券后，在体验项目专区点击使用。</li>
                    <li>5、新手体验项目不可转让。</li>
                    <li>6、为防止不法分子恶意刷取平台奖励，红包奖励需投资真实项目后方可提现。</li>
                    <li>本活动规则解释权归拓天速贷所有，如有疑问请联系在线客服或拨打400-169-1188</li>
                </ul>
            </div>
        </div>
        <div class="bg-w borderBox mt-20 project-model">
            <div class="model-nav">
                <h3>安全保障</h3>
            </div>
            <div class="model-content">
                <ul class="text-list">
                    <li>
                        <p class="intro-title">资金保障</p>
                        <p class="intro-icon icon-one"></p>
                        <p class="intro-text"><span>拓天速贷平台项目都是抵押债权，每笔债权都对应着相应的抵押物。如果投资过程中发生了资金风险，抵押物资将会被处理来为用户的资金提供保障。</span></p>
                    </li>
                    <li>
                        <p class="intro-title">交易保障</p>
                        <p class="intro-icon icon-two"></p>
                        <p class="intro-text"><span>拓天速贷接入了联动优势电子商务有限公司的资金托管系统。交易过程中的充值、投资、提现都在第三方支付平台进行，保证了资金流转的透明和安全。</span></p>
                    </li>
                    <li>
                        <p class="intro-title">信息保障</p>
                        <p class="intro-icon icon-three"></p>
                        <p class="intro-text"><span>采用银行级别的数据传输加密技术，保障用户的信息安全。在同城和异地均建立灾备设备，避免因自然灾害导致用户信息的损失</span></p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="free-success" id="freeSuccess">
        <div class="free-content">
            <div class="free-top">恭喜您体验成功!<span class="close-free"></span></div>
            <div class="free-detail">
                <p class="text-info">并获得以下奖励：</p>
                <p>
                    <img src="${staticServer}/images/sign/actor/gift-free.png" width="100%" alt=""/>
                </p>
                <p class="free-use">
                    <a href="/loan-list" class="btn">立即使用</a>
                </p>
                <p class="free-tip <@global.role hasRole="'INVESTOR'">hide</@global.role>">
                    温馨提示：体验收益发放后需实名认证并进行过投资后方可提现<a href="/register/account">立即认证>></a>
                </p>
            </div>
        </div>
    </div>
    <#include "coupon-alert.ftl" />
</div>
    <#include "red-envelope-float.ftl" />
</@global.main>