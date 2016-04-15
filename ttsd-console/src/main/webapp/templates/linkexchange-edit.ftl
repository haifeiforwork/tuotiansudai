<#import "macro/global.ftl" as global>
<@global.main pageCss="" pageJavascript="linkexchange-edit.js" headLab="announce-manage" sideLab="linkexchangeMan" title="添加友链">

<!-- content area begin -->
<div class="col-md-10">
    <div class="row">
        <form class="form-horizontal jq-form">
            <input type="hidden" class="jq-id" value="${(linkexchange.id?string('0'))!}">

            <div class="form-group">
                <label class="col-sm-1 control-label">标题: </label>

                <div class="col-sm-4">
                    <input type="text" class="form-control jq-title" placeholder="" datatype="*" errormsg="标题不能为空"
                           <#if linkexchange??>value="${linkexchange.title!}"</#if>>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-1 control-label">链接: </label>

                <div class="col-sm-4">
                    <input type="text" class="form-control jq-linkurl" placeholder="" datatype="*" errormsg="链接地址不能为空"
                           <#if linkexchange??>value="${linkexchange.linkUrl!}"</#if>>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4 form-error">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <button type="button" class="btn jq-btn-form btn-primary jq-save">发布</button>
                    <button type="button" class="btn jq-btn-form btn-primary jq-cancel">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- content area end -->
</@global.main>