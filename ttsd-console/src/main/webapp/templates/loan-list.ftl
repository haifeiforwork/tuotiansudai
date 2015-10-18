<!DOCTYPE html>
<html>
<#import "macro/global.ftl" as global>
<#import "macro/menu.ftl" as menu>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>所有借款</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- link bootstrap css and js -->
    <link href="../style/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../style/libs/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="../js/libs/jquery-1.10.1.min.js"></script> <!-- jquery -->
    <script src="../js/libs/bootstrap.min.js"></script>
    <!-- link bootstrap css and js -->

    <link href="../../style/libs/bootstrap/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="../../js/libs/moment-with-locales.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../js/libs/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#datetimepicker1').datetimepicker();
            $('#datetimepicker2').datetimepicker();
        });

        $(function () {
            $(".search").on("click",function(){
                var status = $(".bs-docs-sidenav").find(".active").find("a").data("status");
                var loanId;
                if ($(".loanId").val() == "") {
                    loanId = 0;
                } else {
                    loanId = $(".loanId").val();
                }
                var loanName = $(".loanName").val();
                var startTime = dateFormate($('#datetimepicker1').find("input").val());
                var endTime = dateFormate($('#datetimepicker2').find("input").val());
                window.location.href = "/loanList/console?status="+status+"&loanId="+loanId+"&startTime="+startTime+"&endTime="+endTime+"&currentPageNo=1&loanName="+loanName+"&pageSize=10";
            });

            function dateFormate(time) {
                if(time == "") {
                    return "";
                }
                var timeArray = time.split(" ");
                var year = timeArray[0].split("/")[2];
                var month = timeArray[0].split("/")[0];
                var day = timeArray[0].split("/")[1];
                var hour;
                if(timeArray[2]=="PM") {
                    hour = parseInt(timeArray[1].split(":")[0])+12;
                } else {
                    hour = timeArray[1].split(":")[0];
                }
                var min = timeArray[1].split(":")[1];
                return year+"-"+month+"-"+day+" "+hour+":"+min;
            }
        });
    </script>
    <link rel="stylesheet" href="../style/index.css">
</head>
<body>
<!-- header begin -->
<@menu.header label="proMan"></@menu.header>
<!-- header end -->

<!-- main begin -->
<div class="main">
    <div class="container-fluid">
        <div class="row">
            <!-- menu sidebar -->
            <div class="col-md-2">
                <ul class="nav bs-docs-sidenav">
                    <li <#if status??><#else>class="active"</#if>>
                        <a href="${requestContext.getContextPath()}/loanList/console?status=&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="">所有借款</a>
                    </li>
                    <li <#if status?? && status=="WAITING_VERIFY">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=WAITING_VERIFY&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="WAITING_VERIFY">初审的借款</a></li>
                    <li <#if status?? && status=="RAISING">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=RAISING&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="RAISING">筹款中借款</a></li>
                    <li <#if status?? && status=="RECHECK">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=RECHECK&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="RECHECK">复审借款</a></li>
                    <li <#if status?? && status=="REPAYING">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=REPAYING&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="REPAYING">还款中的借款</a></li>
                    <li <#if status?? && status=="COMPLETE">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=COMPLETE&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="COMPLETE">完成还款的借款</a></li>
                    <li <#if status?? && status=="CANCEL">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=CANCEL&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="CANCEL">已流标等借款</a></li>
                    <li <#if status?? && status=="OVERDUE">class="active"</#if>><a href="${requestContext.getContextPath()}/loanList/console?status=OVERDUE&loanId=0&startTime=&endTime=&currentPageNo=1&loanName=&pageSize=10" data-status="OVERDUE">逾期借款</a></li>
                    <li><a href="projectManage/star.html">发起借款</a></li>
                </ul>
            </div>
            <!-- menu sidebar end -->

            <!-- content area begin -->
            <div class="col-md-10">
                <form action="" class="form-inline query-build">
                    <div class="form-group">
                        <label for="number">编号</label>
                        <input type="text" class="form-control loanId" id="" placeholder="" value="${(loanId?string('0'))!}">
                    </div>
                    <div class="form-group">
                        <label for="number">项目名称</label>
                        <input type="text" class="form-control loanName" id="" placeholder="" value="${loanName!}">
                    </div>
                    <div class="form-group">
                        <label for="number">日期</label>

                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control" value="${(startTime?string('yyyy-MM-dd HH:mm'))!}"/>
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                        </div>
                        -
                        <div class='input-group date' id='datetimepicker2'>
                            <input type='text' class="form-control" value="${(endTime?string('yyyy-MM-dd HH:mm'))!}"/>
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                        </div>
                    </div>

                    <!--<div class="form-group">-->
                    <!--<label for="">角色</label>-->
                    <!--<select class="form-control">-->
                    <!--<option>全部</option>-->
                    <!--<option>Ketchup</option>-->
                    <!--<option>Relish</option>-->
                    <!--</select>-->
                    <!--</div>-->

                    <button type="button" class="btn btn-sm btn-primary search">查询</button>
                </form>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover ">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>项目名称</th>
                            <th>借款人</th>
                            <th>借款金额</th>
                            <th>借款期限</th>
                            <th>年化/活动(利率)</th>
                            <th>项目状态</th>
                            <th>发起时间</th>
                            <th>投资/还款记录</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list loanListDtos as loanListDto>
                        <tr>
                            <td>${loanListDto.id?string('0')}</td>
                            <td class="projectName"><a href="#" title="${loanListDto.name}">${loanListDto.name}</a></td>
                            <td>${loanListDto.agentLoginName}</td>
                            <td>${loanListDto.loanAmount/100}</td>
                            <td>${loanListDto.periods}</td>
                            <td>${loanListDto.basicRate}/${loanListDto.activityRate}</td>
                            <td>${loanListDto.status.getDescription()}</td>
                            <td>${loanListDto.createdTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>投资/还款记录</td>
                            <td>编辑</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <!-- pagination  -->
                <nav>
                    <div>
                        <span class="bordern">总共${loanListCount}条,每页显示${pageSize}条</span>
                    </div>
                <#if loanListDtos?has_content>
                    <ul class="pagination">

                        <li>
                            <#if hasPreviousPage >
                            <a href="?status=${status!}&currentPageNo=${currentPageNo-1}&pageSize=${pageSize}&loanId=${(loanId?string('0'))!}&startTime=${(startTime?string('yyyy-MM-dd HH:mm'))!}&endTime=${(endTime?string('yyyy-MM-dd HH:mm'))!}&loanName=${loanName!}" aria-label="Previous">
                            <#else>
                            <a href="#" aria-label="Previous">
                            </#if>
                            <span aria-hidden="true">&laquo; Prev</span>
                        </a>
                        </li>
                        <li><a>${currentPageNo}</a></li>
                        <li>
                            <#if hasNextPage >
                            <a href="?status=${status!}&currentPageNo=${currentPageNo+1}&pageSize=${pageSize}&loanId=${(loanId?string('0'))!}&startTime=${(startTime?string('yyyy-MM-dd HH:mm'))!}&endTime=${(endTime?string('yyyy-MM-dd HH:mm'))!}&loanName=${loanName!}" aria-label="Next">
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
        </div>
    </div>
</div>
<!-- main end -->

</body>
</html>