<!DOCTYPE html>
<html>
<#import "macro/menu.ftl" as menu>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>ծȨ����ƻ�</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="style/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../style/libs/bootstrap-datepicker.css" rel="stylesheet">
    <link rel="stylesheet" href="../../style/libs/bootstrap-select.css"/>
    <link href="../../style/libs/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">

    <link rel="stylesheet" href="style/index.css">

    <script type="text/javascript" src="js/libs/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="../js/libs/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../js/libs/bootstrap-select.js"></script>
    <script type="text/javascript" src="../../js/libs/bootstrap-datepicker.js"></script>

    <script type="text/javascript">
        $(function() {
            $('#RewardDate .date,#investDate .date').datepicker({
                format:'yyyy-mm-dd',
                autoclose:true
            });
            $('.selectpicker').selectpicker();
        })
    </script>
</head>
<body>
<@menu.header label="finaMan"></@menu.header>
<div class="main">
    <div class="container-fluid">
        <div class="row">
        <@menu.sidebar headLab="finaMan" sideLab="userInvest"></@menu.sidebar>
            <!-- content area begin -->

            <div class="col-md-10">
                <form action="" class="form-inline query-build">
                    <div class="row">
                        <div class="form-group">
                            <label for="control-label">�Ƽ���</label>
                            <input type="text" class="form-control" >
                        </div>
                        <div class="form-group">
                            <label for="control-label">Ͷ����</label>
                            <input type="text" class="form-control" >
                        </div>
                        <div class="form-group" id="investDate">
                            <label for="control-label">Ͷ��ʱ��</label>
                            <div class='input-group date'>
                                <input type='text' class="form-control" />
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                            </div>
                            -
                            <div class='input-group date'>
                                <input type='text' class="form-control"/>
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="control-label">�Ƽ��㼶</label>
                            <input type="text" class="form-control" >
                        </div>
                        <div class="form-group" id="RewardDate">
                            <label for="control-label">����ʱ��</label>
                            <div class='input-group date'>
                                <input type='text' class="form-control" />
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                            </div>
                            -
                            <div class='input-group date'>
                                <input type='text' class="form-control"/>
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="control-label">�Ƽ��˽�ɫ</label>
                            <select class="selectpicker"  data-style="btn-default" >
                                <option>ȫ��</option>
                                <option>ȫ��1</option>
                            </select>
                        </div>
                        <button class="btn btn-primary" type="submit">��ѯ</button>
                        <button class="btn btn-default" type="submit">����</button>
                    </div>

                </form>

                <div class="row">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>��Ŀ����</th>
                            <th>����</th>
                            <th>Ͷ����</th>
                            <th>Ͷ��������</th>
                            <th>Ͷ�ʽ��</th>
                            <th>Ͷ��ʱ��</th>
                            <th>�Ƽ���</th>
                            <th>�Ƽ�������</th>
                            <th>�Ƽ����Ƿ�ҵ��Ա</th>
                            <th>�Ƽ��㼶</th>
                            <th>�Ƽ�����</th>
                            <th>����״̬</th>
                            <th>����ʱ��</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>terer</td>
                            <td>12��</td>
                            <td>jiaojiao</td>
                            <td>����</td>
                            <td>50</td>
                            <td>2015-05-09</td>
                            <td>terer</td>
                            <td>����</td>
                            <td>��</td>
                            <td>1</td>
                            <td>0.1</td>
                            <td>������</td>
                            <td>2015-08-09</td>
                        </tr>
                        <tr>
                            <td>terer</td>
                            <td>12��</td>
                            <td>jiaojiao</td>
                            <td>����</td>
                            <td>50</td>
                            <td>2015-05-09</td>
                            <td>terer</td>
                            <td>����</td>
                            <td>��</td>
                            <td>1</td>
                            <td>0.1</td>
                            <td>������</td>
                            <td>2015-08-09</td>
                        </tr>
                        <tr>
                            <td>terer</td>
                            <td>12��</td>
                            <td>jiaojiao</td>
                            <td>����</td>
                            <td>50</td>
                            <td>2015-05-09</td>
                            <td>terer</td>
                            <td>����</td>
                            <td>��</td>
                            <td>1</td>
                            <td>0.1</td>
                            <td>������</td>
                            <td>2015-08-09</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="row">
                    <!-- pagination  -->
                    <nav>
                        <div><span class="bordern">�ܹ�5��,ÿҳ��ʾ15��</span></div>
                        <ul class="pagination">

                            <li>
                                <a href="#">
                                    <span>? Prev</span>
                                </a>
                            </li>
                            <li><a>1</a></li>
                            <li>
                                <a href="#">
                                    <span>Next ?</span>
                                </a>

                            </li>
                        </ul>
                    </nav>
                </div>
            </div>

            <!-- content area end -->
        </div>
    </div>
</div>
<!-- main end -->
</body>
</html>