<#import "../macro/global-dev.ftl" as global>
<#assign jsName = 'hero_ranking_2017' >

<#assign js = {"${jsName}":"http://localhost:3008/activity/js/${jsName}.js"} >
<#assign css = {"${jsName}":"http://localhost:3008/activity/js/${jsName}.css"}>

<@global.main pageCss="${css.hero_ranking_2017}" pageJavascript="${js.hero_ranking_2017}" activeNav="" activeLeftNav="" title="周年庆活动_拓天活动_拓天速贷" keywords="拓天速贷,拓天活动.生日活动,生日月特权" description="拓天速贷专属生日月特权,生日月投资收益翻倍,拓天速贷专属活动超高收益等你拿.">

<div class="banner-slide"></div>
<div class="activity-page-frame page-width" id="activityPageFrame">

    <div class="rule-box">
        <dl class="title-rule">
            <dt>活动规则</dt>
            <dd>活动期间，每天24点计算当日新增投资排名，上榜者可获丰厚奖励。</dd>
        </dl>

        <div class="reward-list">
            <dl class="reward-one">
                <dt><img src="${commonStaticServer}/images/sign/aboutus/company.jpg"  id="rewardOne"></dt>
                <dd>第1名</dd>
            </dl>

            <dl class="coupon-one">
                <dt></dt>
                <dd>第2～4名</dd>
            </dl>
            <dl class="coupon-two">
                <dt></dt>
                <dd>第5～10名</dd>
            </dl>
        </div>
    </div>

    <div class="heroes-list clearfix">
        <div class="title-head"></div>

        <dl class="sort-box" id="sortBox">
            <dd class="fl">日期：<i class="date"> 2017.07.31</i></dd>
            <dd class="ranking">
                <span class="show-login">登录后查看</span>
                <@global.isAnonymous>我的排名：<span class="show-login">登录后查看</span></@global.isAnonymous>
                <@global.isNotAnonymous>
                    <#--<#if investRanking &gt; 20 || investRanking == 0>未上榜<#else>我的排名：${investRanking}</#if>-->
                    我的排名:<i class="ranking-order">3</i>
                </@global.isNotAnonymous>


            </dd>
            <dd class="fr">今日投资总额：<i class="total">20999000</i>元</dd>
        </dl>
        <div class="nodata-invest tc" style="display: none;">活动已结束</div>
        <table class="table-reward">
            <thead>
            <tr>
                <th width="20%">排名</th>
                <th width="25%">用户</th>
                <th width="25%">投资额</th>
                <th >奖励</th>
            </tr>
            </thead>
            <tbody id="investRanking-tbody">
            <tr>
                <td>1</td>
                <td>涨涨</td>
                <td>900000</td>
                <td>128G iphone7 plus</td>
            </tr>
            <tr>
                <td>1</td>
                <td>涨涨</td>
                <td>900000</td>
                <td>128G iphone7 plus</td>
            </tr>
            <tr>
                <td>1</td>
                <td>涨涨</td>
                <td>900000</td>
                <td>128G iphone7 plus</td>
            </tr>
            <tr>
                <td>1</td>
                <td>涨涨</td>
                <td>900000</td>
                <td>128G iphone7 plus</td>
            </tr>
            </tbody>
        </table>

        <script type="text/template" id="tplTable">
            <% for(var i = 0; i < records.length; i++) { %>
            <% var item = records[i];
            var reward;
            if(type=='invest') {
            if(i==0) {
            reward='神秘大奖＋1%加息券';
            }
            else if(i>0 && i<5) {
            reward='200元红包＋1%加息券';
            }
            else {
            reward='100元红包＋1%加息券';
            }
            }
            else if(type=='referrer') {
            if(i==0) {
            reward='100元红包';
            }
            else if(i==1) {
            reward='50元红包';
            }
            else if(i==2) {
            reward='30元红包';
            }
            }

            %>
            <tr>
                <td><%=i+1%></td>
                <td><%=item.loginName%></td>
                <td><%=item.centSumAmount%></td>
                <td><%=reward%></td>
            </tr>
            <% } %>

        </script>

        <div class="date-button" id="investRanking-button">
            <span class="button-small" id="heroPre">查看前一天</span>
            <span class="btn-to-invest" id="toInvest">立即投资抢占排行榜</span>
            <span class="button-small" id="heroNext">查看后一天</span>
        </div>

    </div>

    <div class="note-box">
        <b>温馨提示：</b>
        1、活动期间，每天24点计算当日新增投资排名，投资者在当日24点之前进行的多次投资，金额可累计计算； <br/>
        2、排行榜仅限于直投项目，其余投资不计入今日投资金额中；<br/>
        3、排行榜中奖人数最多10名，如遇金额一致，则当日先达到该投资额的用户优先获奖，其他用户名次顺延；<br/>
        4、每日投资排行榜排名将在活动页面实时更新，加息券奖励在每日24点榜单生成后即时发放，实物奖品将于活动结束后七个工作日内统一安排发放；<br/>
        5、拓天速贷会根据活动的情况，以等值、增值为基础调整奖品类型；<br/>
        6、为了保证获奖结果的公平性，实物大奖获奖用户在活动期间所进行的所有投标不允许进行债权转让，否则奖品将不予发放；<br/>
        7、拓天速贷在法律范围内保留对本活动的最终解释权。
    </div>
</div>

</@global.main>