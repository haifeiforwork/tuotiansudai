<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "macro/global.ftl" as global>
<@global.main pageCss="" pageJavascript="" headLab="activity-manage" sideLab="IphoneX" title="iPhoneX活动">

<!-- content area begin -->
<div class="col-md-10">
    <div class="col-md-3">总投资额:${(sumInvest/100)?string('0.00')}元</div>
    <div class="col-md-3">总累计年化投资额:${(sumAnnualized/100)?string('0.00')}元</div>
    <div class="col-md-3">总现金奖励:${(sumCash/100)?string('0.00')}元</div>
    <div class="table-responsive">
        <table class="table table-bordered table-hover ">
            <thead>
            <tr>
                <th>
                    姓名
                </th>
                <th>
                    用户名
                </th>
                <th>
                    手机号
                </th>
                <th>
                    活动期内投资金额
                </th>
                <th>
                    累计年化投资额
                </th>
                <th>
                    现金奖励
                </th>
            </tr>
            </thead>
            <tbody>
                <#list data.records as item>
                <tr>
                    <td>
                    ${item.userName!}
                    </td>
                    <td>
                    ${item.loginName!}
                    </td>
                    <td>
                    ${item.mobile!}
                    </td>
                    <td>
                    ${item.sumInvestAmount!}
                    </td>
                    <td>
                    ${item.sumAnnualizedAmount!}
                    </td>
                    <td>
                    ${item.reward!}
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>

    <a href="/activity-console/activity-manage/iphonex" class="form-control" style="width: 200px">请点击此处导出EXCEl</a>
    <br/>
    <!-- pagination  -->
    <nav>
        <div>
            <span class="bordern">总共${data.count}条,每页显示${data.pageSize}条</span>
        </div>
        <#if data?has_content>
            <ul class="pagination">
                <li>
                    <#if data.hasPreviousPage>
                    <a href="?index=${data.index - 1}"
                       aria-label="Previous">
                    <#else>
                    <a href="#" aria-label="Previous">
                    </#if>
                    <span aria-hidden="true">&laquo; Prev</span>
                </a>
                </li>
                <li><a>${data.index}</a></li>
                <li>
                    <#if data.hasNextPage>
                    <a href="?index=${data.index + 1}"
                       aria-label="Next">
                    <#else>
                    <a href="#" aria-label="Next">
                    </#if>
                    <span aria-hidden="true">Next &raquo;</span>
                </a>
                </li>
            </ul>
        </#if>
    </nav>
    <!-- pagination -->
</div>
<!-- content area end -->
</@global.main>