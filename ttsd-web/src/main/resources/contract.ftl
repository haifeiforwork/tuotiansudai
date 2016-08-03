<!DOCTYPE html>
<html >
<head>
    <style type="text/css" mce_bogus="1">

        body{font-family: 宋体, SimSun}
    </style>

</head>
<body>
<p><span style="font-family: 宋体, SimSun;">编号：${loanId}</span></p>

<p style="text-align: center;"><span style="font-family: 宋体, SimSun;"><strong><span
        style="font-family: 宋体, SimSun; font-size: 20px;">借款协议</span></strong><br/></span></p>

<p><span style="font-family: 宋体, SimSun;">甲方（借款人）：<span style="font-family: 宋体, SimSun;">${loanerUserName}</span></span>
</p>

<p><span style="font-family: 宋体, SimSun;">甲方（借款人)平台账号：<span style="font-family: 宋体, SimSun;">${loanerLoginName}</span></span>
</p>

<p><span style="font-family: 宋体, SimSun;">身份证号：<span style="font-family: 宋体, SimSun;">${loanerIdentityNumber}</span></span></p>

<p><br/></p>

<p><span style="font-family: 宋体, SimSun;">代理人：<span style="font-family: 宋体, SimSun;">${agentUserName}</span></span></p>

<p><span style="font-family: 宋体, SimSun;">代理人平台账号：<span style="font-family: 宋体, SimSun;">${agentLoginName}</span></span></p>

<p><span style="font-family: 宋体, SimSun;">身份证号：<span style="font-family: 宋体, SimSun;">${agentIdentityNumber}</span></span></p>

<p><span style="font-family: 宋体, SimSun;"><br/></span></p>

<p><span style="font-family: 宋体, SimSun;">乙方（出借人）：</span></p>

<#--<p><span style="font-family: 宋体, SimSun;">${investList}</span></p>-->

<table width="686px" cellspacing="0" border="1" cellpadding="4">
    <tr>
        <th>平台账号</th>
        <th>真实姓名</th>
        <th>身份证号</th>
        <th>出借金额</th>
        <th>借款期限</th>
        <th>投资确认日期</th>
    </tr>
<#list investList as investor>
    <tr>
        <td>${investor.loginName}</td>
        <td>${investor.userName}</td>
        <td>${investor.identityNumber}</td>
        <td>${investor.investAmount}</td>
        <td>${investor.period}</td>
        <td>${investor.investTime}</td>
    </tr>
</#list>
</table>

<p><span style="font-family: 宋体, SimSun;"><br/></span></p>

<p><span style="font-family: 宋体, SimSun;">丙方（平台）：拓天伟业（北京）金融信息服务有限公司</span></p>

<p><span style="font-family: 宋体, SimSun;">住所地：北京市平谷区林荫北街13号信息大厦802室&nbsp;</span></p>

<p><br/></p>

<p><span style="font-family: 宋体, SimSun;">上诉主体单称&quot;一方&quot;，合称&quot;各方&quot;。</span></p>

<p><br/></p>

<p><strong><span style="font-family: 宋体, SimSun;">鉴于：</span></strong></p>

<p>1、丙方为本协议居间平台（互联网域名为www.tuotiansudai.com，简称“拓天速贷”）的运营管
    理人，为客户提供金融信息咨询及相关服务。</p>

<p>2、甲方、乙方同意遵守丙方平台的各项行为准则，在充分详细阅读并理解本文本情形下本着诚信、
    公平、自愿的原则签订本《借款协议》。</p>

<p style="margin-bottom: 3px;line-height: 36px"><span
        style="font-size: 16px;font-family: 仿宋">现各方根据平等、自愿的原则，达成本协议如下：</span></p>

<p><strong>一、<span class="Apple-tab-span" style="white-space: pre;"></span>释义</strong></p>

<p><strong>在本合同中，除非上下文另有解释，下列词语具有以下含义：</strong></p>

<p>1.1 出借人：指自主选择出借一定数量资金给他人的自然人。</p>

<p>1.2 借款人：指有资金需求、经丙方平台发布借款需求信息并得到出借人一定数量出借资金的自然人。</p>

<p>1.3 代理人：指受借款人委托在借款额度内向拓天速贷平台申请融资及代理其完成相关还款手续的自然
    人或资质合法的机构。</p>

<p>1.4 债权：指出借人与借款人借贷关系存续期间出借人拥有的全部权益，债权以人民币计价。&nbsp;</p>

<p>1.5 工作日：指中华人民共和国法律规定的工作日（法定工作日）。&nbsp;</p>

<p>1.6 提前还款：指在出借人与借款人借贷关系存续期间约定了借款人的本息偿还周期和金额等相关还款
    计划，借款人可能在协议规定的偿还周期结束前，在某一期将剩余本金提前偿还给出借人，从而使
    出借人的资金比约定的计划提前收回。</p>

<p><br/></p>

<p><strong>二、甲方向乙方借款，借款信息如下：&nbsp;</strong></p>

<table border="1" cellspacing="0" cellpadding="4" width="686">
    <tr>
        <th>借款用途</th>
        <th>${loanPurpose}</th>
        <th>百</th>
        <th>十</th>
        <th>万</th>
        <th>千</th>
        <th>百</th>
        <th>十</th>
        <th>元</th>
        <th>角</th>
        <th>分</th>
    </tr>
    <tr>
        <td>借款本金数额</td>
        <td>¥${actualMoney}</td>
        <td>${million}</td>
        <td>${hundredThousand}</td>
        <td>${tenThousand}</td>
        <td>${thousand}</td>
        <td>${hundred}</td>
        <td>${ten}</td>
        <td>${yuan}</td>
        <td>${bugle}</td>
        <td>${fen}</td>
    </tr>
    <tr>
        <td>还款分期月数</td>
        <td>${deadline}</td>
        <td colspan="2">还款日</td>
    <#--<td colspan="7">${repayDay}日(24:00前，节假日不顺延)</td>-->
        <td colspan="7">详见回款详情</td>
    </tr>
    <tr>
        <td>还款起止时间</td>
        <td colspan="10">${interestBeginTime}起，至 ${interestEndTime}止</td>
    </tr>
</table>

<p><br/></p>

<p>三、<span class="Apple-tab-span" style="white-space: pre;"></span>资金出借方式</p>

<p>3.1 甲方将其资金需求信息发布在丙方运营管理的平台上，乙方与甲方签订出资协议，且甲方收到乙方
    出借款后，乙方取得甲方债权。</p>

<p>3.2 乙方进行出借资金操作之前，有义务仔细阅读本协议，充分了解并清楚知晓相应权利义务，在确认
    同意本协议各条款后,乙方点击“确认投资”选项即视为同意签署本协议,以及视为乙方已向丙方发出不
    可撤销的授权指令，同意授权丙方委托其指定的第三方支付机构冻结乙方确定出借的资金；冻结将
    在本协议生效或本协议失效时解除。</p>

<p>3.3 本协议在借款项目所需的债权金额已全部认缴，且所有出借人的出借资金均已全部冻结时立即生
    效。若借款项目所需债权金额在规定时间内未被全部认缴，则本协议失效，冻结立即解除。</p>

<p>3.4 本协议生效时，即视为乙方不可撤销地同意授权丙方委托其指定的第三方支付机构将冻结资金划转
    至甲方或其代理人在第三方支付机构的账户，从而完成资金的投资。</p>

<p>3.5 若出现由于丙方原因导致的划扣错误，则丙方返还错误划扣款项并按照银行活期存款利率的1倍支
    付乙方利息。</p>

<p><br/></p>

<p>四、<span class="Apple-tab-span" style="white-space: pre;"></span>甲方的权利义务</p>

<p>4.1 甲方系中华人民共和国公民，具有签署和履行本协议及与本协议有关的其他文件的民事权利能力和
    民事行为能力，能够独立承担民事责任。</p>

<p>4.2 甲方签署并履行本协议不违反对其有约束力或有影响的法律法规或协议的限制；本协议签署后，本
    协议约定即形成对其合法、有效和有约束力的义务。</p>

<p>4.3 甲方代理人确保其提供的借款人真实存在，乙方出借后形成的民间个人债权债务关系真实存在，否
    则甲方代理人承担因该债权债务关系不存在而对乙方造成的损失。</p>

<p>4.4 未经乙方事先书面（包括但不限于电子邮件等方式）同意，甲方不得将本协议项下的任何权利
    义务转让给任何第三方。</p>

<p>4.5 如甲方为本次借款提供的抵押物依法律法规规定需办理登记手续，甲方及甲方代理人有义务协助乙
    方办理抵押物相关登记手续。</p>

<p><br/></p>

<p>五、<span class="Apple-tab-span" style="white-space: pre;"></span>乙方权利义务</p>

<p>5.1 乙方系中华人民共和国公民，具有签署和履行本协议及与本协议有关其他文件的民事权利能力和民
    事行为能力，能够独立承担民事责任。</p>

<p>5.2 乙方签署和履行本协议应不违反对其有约束力或有影响的法律法规或协议的限制；在此基础上，本
    协议签署后，即构成对乙方合法、有效和有约束力的义务。</p>

<p>5.3 乙方参考丙方发布的信息后，拥有最终决定是否出借资金给特定借款人的权利。</p>

<p>5.4 乙方享有其所出借款项带来的利息收益。</p>

<p>5.5 乙方同意，如果甲方有提前还款需求时，乙方允许借款人进行提前还款，而无需再特别通知乙方。</p>

<p>5.6 乙方保证其用于出借资金来源合法，乙方是该资金的合法所有人。如果第三人对资金归属、合法性
    问题发生争议，由乙方负责解决。&nbsp;</p>

<p>5.7 乙方变更账户信息、通讯地址、电话等相关重要信息，须及时通知丙方。因乙方未及时通知丙方而
    导致自身受到损失，由乙方自行承担责任。</p>

<p>5.8 对于丙方基于发布资金借款需要而提供给乙方的甲方个人证件、其他相关信用信息及甲方代理人的
    全部相关信息，乙方确保仅用于出借参考，不得向任何甲方、甲方代理人、丙方以外的第三方透露，
    乙方有义务为甲方个人证件、其他相关信用信息及甲方代理人的全部相关信息、丙方的业务内容进
    行保密。如乙方未经允许、不恰当地向第三方透露借款人的信用信息或甲方个人信用信息及甲方代
    理人、丙方的商业秘密，由此对甲方、甲方代理人或丙方造成损失的，由乙方承担全部责任。</p>

<p>5.9 如乙方有效持有的甲方债权符合丙方平台相关债权转让标准的，乙方可以在丙方平台依照丙方平
    台相关债权转让规则转让其持有的甲方债权，乙方承诺除上述约定的债权转让渠道外，其不会通过
    丙方平台以外的任何方式向任何第三方转让其持有的甲方债权。</p>

<p><br/></p>

<p>六、<span class="Apple-tab-span" style="white-space: pre;"></span>丙方的权利与义务</p>

<p>6.1 丙方应当按照本协议的规定，恪尽职守，以诚实、信用、谨慎、有效管理的原则为甲、乙方进行
    服务。</p>

<p>6.2 丙方确保平台发布借款人经过谨慎的信用资质审核；丙方有义务全程协助甲、乙方完成借款手续；
    及时报告甲方出借资金收益变化情况，在确定的报告日向甲方寄送其资金出借情况报告。</p>

<p>6.3 丙方须对甲、乙方个人信息、资产情况及其他服务相关事务的情况和资料依法保密。</p>

<p>6.4 在出借人与借款人借贷关系存续期间，借款人发生违约行为时，丙方须采取合法合理的措施协助乙
    方进行及时催收和追讨。</p>

<p><br/></p>

<p>七、<span class="Apple-tab-span" style="white-space: pre;"></span>出资资金的回收管理</p>

<p>7.1 根据乙方与甲方的借款相关协议文件，甲方有义务对乙方定期还本付息。为便利、统一地回收本息
    ，乙方委托并授权丙方授权第三方支付机构代为划转甲方每期偿还本息，在每期收款日三日内与乙
    方进行结算。</p>

<p>7.2 乙方资金回收专用账户为乙方在丙方授权第三方支付机构注册的账户，划转完毕即视为甲方本期还
    款成功。</p>

<p>7.3 甲方可在借款期间任何时候提前偿还剩余借款。若甲方提前偿还借款，以实际用款时间据实结算利
    息。</p>

<p>7.4 乙方的提现账户为乙方在丙方授权第三方支付机构注册时填写的银行账户。乙方须确保该银行账户
    为其名下合法有效的银行账户，乙方变更该账户时必须签署《出借人客户信息变更表》并经丙方确
    认后方可变更；如因乙方未及时书面通知丙方而引发的损失由乙方自行承担。</p>

<p>7.5 甲方为多人时，任何一位借款人均应履行本协议项下的义务，对全部借款承担连带清偿责任，乙方
    有权向任何一位借款人追偿本合同项下全部应付款项，包括但不限于本金、利息、违约金等。乙方
    （出借人）为多人时，每人按比例享有债权。</p>

<p>7.6 乙方从其在丙方授权的第三方支付机构注册账户内申请提现，乙方资金将在其提现申请后三个工作
    日内到达。</p>

<p><br/></p>

<p>八、<span class="Apple-tab-span" style="white-space: pre;"></span>还款违约及保障措施</p>

<p>8.1 为确保借款人履行融资项目项下的义务，甲方为其本次资金借款提供<span style="text-decoration: underline;">房屋</span><span
        id="_baidu_bookmark_end_101" style="display: none; line-height: 0px;">‍</span><span id="_baidu_bookmark_end_99"
                                                                                            style="display: none; line-height: 0px;">‍</span><span
        id="_baidu_bookmark_end_97" style="display: none; line-height: 0px;">‍</span><span id="_baidu_bookmark_end_95"
                                                                                           style="display: none; line-height: 0px;">‍</span><span
        id="_baidu_bookmark_end_61" style="display: none; line-height: 0px;">‍</span><span id="_baidu_bookmark_end_59"
                                                                                           style="display: none; line-height: 0px;">‍</span><span
        id="_baidu_bookmark_end_57" style="display: none; line-height: 0px;">‍</span><span id="_baidu_bookmark_end_55"
                                                                                           style="display: none; line-height: 0px;">‍</span><span
        id="_baidu_bookmark_end_53" style="display: none; line-height: 0px;">‍</span><span id="_baidu_bookmark_end_51"
                                                                                           style="display: none; line-height: 0px;">‍</span>抵押。
</p>

<p>8.2 每期约定还款日当日，若甲方在丙方授权的第三方账户内余额不足以清偿当月应还款项的，则发生
    甲方还款逾期。</p>

<p>8.3 甲方晚于本协议规定的还款日还款的，则应向乙方支付逾期违约金，违约金的收取标准为借款本金
    的<span style="text-decoration: underline;">${overdue_repay_investor}
    <span id="_baidu_bookmark_start_0" style="display: none; line-height: 0px;">‍</span></span><span
        id="_baidu_bookmark_start_2" style="display: none; line-height: 0px;">‍</span>/日<span id="_baidu_bookmark_end_3"
                                                                                              style="display: none; line-height: 0px;">‍</span><span
        style="text-decoration: underline;"><span id="_baidu_bookmark_end_1"
                                                  style="display: none; line-height: 0px;">‍</span></span>。</p>

<p>8.4 如因甲方原因导致未能结清当月全部欠款，则按本条8.3执行。</p>

<p>8.5 当甲方未按照本协议的约定，连续三期均未足额清偿单期借款本金及利息时，乙方有权宣告借款提
    前到期，并可以选择以下一种方式追索本息：</p>

<p>（1）乙方授权委托丙方或授权丙方委托律师，代理乙方依法追究甲方的违约责任。甲方违约所应
    支付的本金及违约金归乙方所有，违约金的收取标准为借款本金的<span
        style="text-decoration: underline;">${overdue_repay_investor}&nbsp;<span id="_baidu_bookmark_start_6"
                                                                                 style="display: none; line-height: 0px;">‍</span></span><span
        id="_baidu_bookmark_start_8" style="display: none; line-height: 0px;">‍</span><span
        style="text-decoration: underline;"><span id="_baidu_bookmark_start_15"
                                                  style="display: none; line-height: 0px;">‍</span></span><span
        id="_baidu_bookmark_start_17" style="display: none; line-height: 0px;">‍</span>/日<span
        id="_baidu_bookmark_end_18" style="display: none; line-height: 0px;">‍</span><span
        style="text-decoration: underline;"><span id="_baidu_bookmark_end_16"
                                                  style="display: none; line-height: 0px;">‍</span></span><span
        id="_baidu_bookmark_end_9" style="display: none; line-height: 0px;">‍</span><span
        style="text-decoration: underline;"><span id="_baidu_bookmark_end_7"
                                                  style="display: none; line-height: 0px;">‍</span></span>；</p>

<p>（2）甲方代理人协助乙方受让甲方抵押物的抵押权，如依法须办理抵押登记手续，甲方代理人有
    义务配合乙方完成；乙方受让抵押物抵押权后应自行委托律师，依法追究甲方违约责任。甲方违约
    所应支付的本金及违约金归乙方所有
    ，违约金的收取标准为借款本金的<span style="text-decoration: underline;">${overdue_repay_investor}&nbsp;</span><span
            style="text-decoration: underline;"></span>/日。</p>

<p><br/></p>

<p>九、<span class="Apple-tab-span" style="white-space: pre;"></span>风险提示</p>

<p>9.1 政策风险</p>

<p>因宏观政策、财政政策、货币政策、行业政策、地区发展政策等因素引起的风险。</p>

<p>9.2 借款人信用风险</p>

<p>当借款人短期或长期丧失还款能力（包括但不限于借款人收入情况、财产状况发生变化、人身出现
    意外、发生疾病、死亡等情况），或者借款人的还款意愿发生变化时，乙方受让的资金可能无法按
    时回收。</p>

<p>9.3 资金流动性风险</p>

<p>在借款人不主动提前还款的情况下，借款人将按照约定的期限分期偿还乙方的本金和利息，乙方的
    资金将分期回收，因此资金回收需要一定的周期。</p>

<p>9.4 不可抗力</p>

<p>因不可抗力发生的战争、动乱、自然灾害等可能导致甲方资产损失的风险。各方均不承担任何法律
    责任。</p>

<p><br/></p>

<p>十、<span class="Apple-tab-span" style="white-space: pre;"></span>税务处理</p>

<p>甲、乙方在资金出借、转让过程中产生的相关税费，由各方自行向其主管税务机关申报、缴纳，丙
    方不负责相关事宜处理。</p>

<p><br/></p>

<p>十一、<span class="Apple-tab-span" style="white-space: pre;"></span>协议的变更</p>

<p>11.1 本协议的任何修改、补充均须以拓天速贷平台电子文本形式作出。&nbsp;</p>

<p>11.2 变更后的内容或补充协议与本协议具有同等法律效力，如果变更后的内容或补充协议与本协议发生
    冲突，以变更后的内容或补充协议为准。</p>

<p><br/></p>

<p>十二、违约责任<br/></p>

<p><br/>任何一方违反本协议的约定，使得本协议的全部或部分不能履行，均应承担违约责任，并应赔偿因
    其违约而给守约方造成的损失，包括但不限于由此产生的诉讼费和律师费、协议履行后可以获得的
    利益等，但不得超过违反协议一方订立协议时可以预见或应当预见的因违反协议可能造成的损失。
    如多方违约，根据实际情况各自承担相应的责任。
</p>

<p><br/></p>

<p>十三、<span class="Apple-tab-span" style="white-space: pre;"></span>法律适用及争议解决方式</p>

<p>13.1 本协议的签署、生效、履行、解释、修改和终止等事项均适用中华人民共和国现行法律法规。</p>

<p>13.2 各方履行本协议过程中发生争议均应当以友好协商的方式解决，协商不成的，由北京仲裁委员会管
    辖。</p>

<p><br/></p>

<p>十四、<span class="Apple-tab-span" style="white-space: pre;"></span>其他事项</p>

<p>14.1 甲方委托其代理人（代理人信息以页首载明为准）代为收取乙方支付的借款，代其向乙方交付还款
    等，甲方代理人在拓天速贷平台的一切行为均由甲方负责。</p>

<p>14.2 各方已经充分阅读并理解本协议及相关法律文件及其含义，对于本协议项下的行为自愿承担各自的
    法律责任和义务。</p>

<p>14.3 如果本协议的任何条款因任何原因无效，该条款的无效不影响本协议其他条款有效性的，则各方应
    当继续履行本协议其他条款。</p>

<p>14.4 本协议项下相关费用的支付不受法定假日或公休日影响。遇到还款当月无还款日对应日的月份，还
    款日为应还款当月的最后一日。</p>

<p>14.5 本协议项下的各标题仅为行文方便而设，不用于解释本协议。</p>

<p>14.6 各方委托平台保管所有与本协议有关的书面文件或电子信息。</p>

<p>14.7 本协议最终解释权归拓天伟业（北京）金融信息服务有限公司。</p>

<p><br/></p>

<p>十五、丙方服务收费标准</p>

<p>15.1 乙方了解并同意：丙方因其提供的各项服务向乙方按月收取服务费，并按照双方约定所得收益的<span style="text-decoration: underline;">10%<span
        id="_baidu_bookmark_start_2" style="display: none; line-height: 0px;">‍</span></span><span
        id="_baidu_bookmark_start_4" style="display: none; line-height: 0px;">‍</span>的标准收取<span
        id="_baidu_bookmark_end_5" style="display: none; line-height: 0px;">‍</span><span
        style="text-decoration: underline;"><span id="_baidu_bookmark_end_3"
                                                  style="display: none; line-height: 0px;">‍</span></span>，自乙方出借收益中按月扣除。
</p>

<p>15.2 服务费按照出借笔数分别进行对应收取。</p>

<p>15.3 服务费由丙方在每笔出借款对应的回款日收取（在当期借款人还款中自动扣除），单笔服务费=该
    笔出借资金上一个出借情况报告日价值*服务费率。</p>

<p>15.4 首次服务费收取时间为客户第二个资金出借情况报告日。</p>

</body>
</html>