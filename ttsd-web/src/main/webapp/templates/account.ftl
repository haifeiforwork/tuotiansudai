<!DOCTYPE html>
<html>
<#import "macro/global.ftl" as global>
<@global.head title="�Զ�Ͷ��" pageCss="${css.global}">
</@global.head>
<body>
<#include "header.ftl" />
<div class="content">
    <aside class="menuBox fl">
        <ul class="menu-list">
            <li><a href="javascript:">�˻�����</a></li>
            <li><a href="javascript:">Ͷ�ʼ�¼</a></li>
            <li><a href="javascript:">ծȨת��</a></li>
            <li><a href="javascript:">�ʽ����</a></li>
            <li><a href="javascript:">�����ʲ�</a></li>
            <li><a href="javascript:" class="actived">�Զ�Ͷ��</a></li>
            <li><a href="javascript:">���ֺ��</a></li>
            <li><a href="javascript:">�Ƽ�����</a></li>
        </ul>
    </aside>
    <div class="recharge-container fr autoHeight">
        <h4><em class="tc">�Զ�Ͷ��</em></h4>

        <div class="recharge-content pad-s">

        </div>

    </div>
</div>


<#include "footer.ftl">
<@global.javascript pageJavascript="${js.autoInvest}">
</@global.javascript>
</body>
</html>