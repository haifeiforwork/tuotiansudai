<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.my_account}" pageJavascript="${js.point}" activeNav="我的账户" activeLeftNav="我的财豆" title="我的财豆">

<div class="content-container my-choi-beans">
    <h4 class="column-title">
        <em class="tc title-navli active">我的财豆</em>
        <em class="tc title-navli">财豆兑换</em>
        <em class="tc title-navli">财豆明细</em>
    </h4>

    <div class="content-list">
		<div class="choi-beans-list active">
			<div class="beans-intro">
				<div class="beans-list">
					<i class="icon-result icon-beans"></i>
					<span class="title-text">财豆总览</span>
					<span class="title-href">财豆明细></span>
				</div>
				<div class="beans-list mt-20">
					<span class="beans-num">可用财豆：10000</span>
					<i class="icon-result icon-dou"></i>
				</div>
				<div class="beans-list mt-20">
					<ul class="beans-recent">
						<li class="one-day">
							<p>
								<i class="icon-circle"></i>
								<span class="text-date">2月16日</span>
								<span class="text-money">
									<strong>+1000</strong>
									<i class="icon-result icon-sm-dou"></i>
								</span>
							</p>
						</li>
						<li class="two-day">
							<p>
								<i class="icon-circle"></i>
								<span class="text-date">2月16日</span>
								<span class="text-money">
									<strong>+1000</strong>
									<i class="icon-result icon-sm-dou"></i>
								</span>
							</p>
						</li>
						<li class="three-day">
							<p>
								<i class="icon-circle"></i>
								<span class="text-date">2月16日</span>
								<span class="text-money">
									<strong>+1000</strong>
									<i class="icon-result icon-sm-dou"></i>
								</span>
							</p>
						</li>
					</ul>
				</div>
			</div>
			<div class="beans-operat">
				<h3>赚取财豆</h3>
				<ul class="object-list">
					<li>
						<p class="icon-com invest-bg"><i class="icon-result icon-invest"></i></p>
						<p class="btn-list">
							<a href="#"><span class="btn-invest">去投资</span></a>
						</p>
					</li>
					<li>
						<p class="icon-com sign-bg"><i class="icon-result icon-sign"></i></p>
						<p class="btn-list">
							<span class="btn-sign" id="signBtn">签到</span>
						</p>
					</li>
					<li>
						<p class="icon-com task-bg"><i class="icon-result icon-task"></i></p>
						<p class="btn-list">
							<span class="btn-task" id="taskBtn">做任务</span>
						</p>
					</li>
				</ul>
			</div>
			<div class="beans-infotext">
				<dl>
					<dt>财豆说明：</dt>
					<dd>1.财豆可用于兑换体验券、加息券等优惠券；</dd>
					<dd>2.在平台投资，投资金额与所获财豆的比率为1：1，即每投资100元现金奖励100个财豆；</dd>
					<dd>3.连续签到，财豆翻倍送！第一天签到领5财豆，最多每天可领80财豆；</dd>
					<dd>4.完成任务，赠送财豆，完成任务越多，赠送财豆越多；</dd>
					<dd>5.在平台进行抽奖，最高可抽88888财豆，每次抽奖消耗10财豆；</dd>
					<dd>6.财豆不可以提现，不可以转让，不可以用于其他平台。</dd>
				</dl>
			</div>
		</div>
    	<div class="choi-beans-list">
			<ul class="coupon-list">
			    <li class="yellow-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>
			                    现金红包
			                </span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">2</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[投资成功放款返现]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="yellow-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>现金红包</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">15</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[单笔投资满3000元可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="yellow-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>现金红包</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">50</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[单笔投资满1万元可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="yellow-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>现金红包</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">120</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[单笔投资满3万元可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="yellow-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>现金红包</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">200</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[单笔投资满5万元可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="yellow-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>现金红包</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">500</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[单笔投资满10万元可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="new-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>新手体验券</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">5000</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[在拓天平台投资可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="new-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>投资体验券</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">10000</span>
			                    <span class="unit-text">元</span>
			                </p>
			                <p>[单笔投资满5万元可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			    <li class="bite-type">
			        <div class="top-com">
			            <div class="left-name">
			                <span>加息券</span>
			                <em></em>
			                <i class="circle-top"></i>
			                <i class="circle-bottom"></i>
			            </div>
			            <div class="right-coupon">
			            	<div class="coupon-time">有效期：30天</div>
			                <p class="mt-10">
			                    <span class="num-text">0.2%</span>
			                    <span class="unit-text">年化收益</span>
			                </p>
			                <p>[限投资5万元以内可用]</p>
			                <p>产品限制：<i class="pro-icon">稳<em class="bg-com"></em><em class="circle-com"></em></i><i class="pro-icon">久<em class="bg-com"></em><em class="circle-com"></em></i>产品线可用</p>
			            </div>
			        </div>
			        <div class="bottom-time">
			            <span>所需财豆：3000</span>
			            <i class="icon-dou"></i>
			            <a href="javascript:void(0)" class="reedom-now" data-id="10" data-bite="0.2">立即兑换</a>
			        </div>
			    </li>
			</ul>
    	</div>
    	<div class="choi-beans-list invest-list-content">
    		<div class="item-block date-filter">
		        <span class="sub-hd">起止时间:</span>
		        <input type="text" id="date-picker" class="input-control" size="35"/>
		        <span class="select-item" data-day="1">今天</span>
		        <span class="select-item" data-day="7">最近一周</span>
		        <span class="select-item current" data-day="30">一个月</span>
		        <span class="select-item" data-day="180">六个月</span>
		        <span class="select-item" data-day="">全部</span>
		    </div>

		    <div class="item-block status-filter">
		        <span class="sub-hd">往来类型:</span>
		        <span class="select-item current" data-status="">全部</span>
		        <span class="select-item" data-status="SIGN_IN">已获得</span>
		        <span class="select-item" data-status="EXCHANGE">已使用</span>
		    </div>
		    <div class="clear-blank"></div>
		    <div class="point-bill-list">

		    </div>
		    <div class="pagination" data-url="/point" data-page-size="10">
		    </div>
    	</div>
    </div>
    <div class="sign-layer" id="signLayer">
        <div class="sign-layer-list">
            <div class="sign-top">
                <div class="close-btn" id="closeSign"></div>
                <p class="sign-text">签到成功，领取5财豆！</p>

                <p class="tomorrow-text">明日可领10财豆</p>

                <p class="img-beans">
                    <img src="${staticServer}/images/sign/sign-beans.png"/>
					<span class="add-dou">
						+5
					</span>
                </p>

                <p class="intro-text">连续签到，财豆翻倍送，最多每天可领<span>80</span>财豆！</p>
            </div>
            <div class="sign-bottom">
                <ul>
                    <li>
                        <p class="day-name">第1天</p>

                        <p class="day-beans">
                            <span>5</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li>
                        <p class="day-name">第2天</p>

                        <p class="day-beans">
                            <span>10</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li>
                        <p class="day-name">第3天</p>

                        <p class="day-beans">
                            <span>20</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li>
                        <p class="day-name">第4天</p>

                        <p class="day-beans">
                            <span>40</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li>
                        <p class="day-name">第5天</p>

                        <p class="day-beans">
                            <span>80</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li>
                        <p class="day-name">第6天</p>

                        <p class="day-beans">
                            <span>80</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li>
                        <p class="day-name">第7天</p>

                        <p class="day-beans">
                            <span>80</span>
                            <i class="bean-img"></i>
                        </p>
                    </li>
                    <li class="last-day">
                        <p class="day-name">第N天</p>

                        <p class="day-beans">
                            <span>...</span>
                        </p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="task-layer" id="taskLayer">
        <div class="task-layer-list">
            <h3>
                <span>完成任务送财豆</span>

                <div class="close-btn" id="closeTask"></div>
            </h3>
            <ul>
                <#if pointTaskDtos?? && pointTaskDtos?size gt 0>
                    <#list pointTaskDtos as pointTaskDto>
                        <li>
                            <p>任务${pointTaskDto_index + 1}：${pointTaskDto.name.getTitle()}</p>

                            <p>说明：${pointTaskDto.name.getDescription()}</p>

                            <p>奖励：${pointTaskDto.point}财豆</p>
                            <#if pointTaskDto.completed>
                                <p>状态：已完成</p>
                            <#else >
                                <#switch pointTaskDto.name>
                                    <#case "REGISTER">
                                        <#assign taskLink="/register/user">
                                        <#break>
                                    <#case "BIND_EMAIL">
                                        <#assign taskLink="/personal-info">
                                        <#break>
                                    <#case "BIND_BANK_CARD">
                                        <#assign taskLink="/bind-card">
                                        <#break>
                                    <#case "FIRST_RECHARGE">
                                        <#assign taskLink="/recharge">
                                        <#break>
                                    <#case "FIRST_INVEST">
                                        <#assign taskLink="/loan-list">
                                        <#break>
                                    <#case "SUM_INVEST_10000">
                                        <#assign taskLink="/loan-list">
                                        <#break>
                                </#switch>
                                <p><a href="${taskLink}">立即去完成</a></p>
                            </#if>

                        </li>
                    </#list>
                </#if>
            </ul>
        </div>
    </div>
</div>
</@global.main>