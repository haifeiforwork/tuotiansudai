<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.create_transfer_detail}" pageJavascript="${js.create_transfer_detail}" activeNav="我的账户" activeLeftNav="我的投资" title="投资记录">
<div class="content-container">
    <h4 class="column-title">
        <a href="/investor/invest-list"><em class="tc">债权转让</em></a>
    </h4>
	<div class="create-transfer-container">
		<h5>可转让债权>><span>申请转让</span></h5>
		<form action="#" method="post" id="createForm">
			<ul class="info-container">
				<li class="info-list">
					<div class="info-left">
						转让价格：
					</div>
					<div class="info-right">
						<input type="text" class="int-number" name="price"></input>
						<span>元</span>
					</div>
				</li>
				<li class="info-list">
					<div class="info-left">
						项目本金：
					</div>
					<div class="info-right">
						1000000.00元
					</div>
				</li>
				<li class="info-list">
					<div class="info-left">
						转让手续费：
					</div>
					<div class="info-right">
						<span>50.00元</span>
						<i class="fa fa-question-circle" aria-hidden="true" title="sfdsfsdfs"></i>
					</div>
				</li>
				<li class="info-list">
					<div class="info-left">
						转让截止时间：
					</div>
					<div class="info-right">
						2016-04-13 0点
					</div>
				</li>
				<li class="info-list tc">
					<i class="fa fa-check-square" aria-hidden="true"></i>
					<span>我已阅读并同意<a href="#">债权转让协议书（范本）</a></span>
				</li>
				<li class="info-list tc">
					<button class="btn btn-normal" type="submit">确定</button>
					<button class="btn btn-default" type="button" id="cancleBtn">取消</button>
				</li>
			</ul>
		</form>
	</div>
</div>
<div class="success-tip" id="successTip">
	<i class="fa fa-check-circle" aria-hidden="true"></i>
	<p>申请转让成功！<br /><span class="count-time">3</span>秒后自动跳转至“转让中债权”</p>
</div>
</@global.main>