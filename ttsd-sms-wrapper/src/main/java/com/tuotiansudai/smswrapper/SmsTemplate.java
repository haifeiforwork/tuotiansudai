package com.tuotiansudai.smswrapper;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public enum SmsTemplate {

    /*
    !!! ATTENTION !!!
    模板中的变量占位符在网易里使用的是 %s, 在阿里里使用的是 ${param0} ${param1}。

    请在阿里平台上配置的时候一定要注意，请严格按照变量在文本中出现的顺序进行编号，编号从0开始。
    请在阿里平台上配置的时候一定要注意，请严格按照变量在文本中出现的顺序进行编号，编号从0开始。
    请在阿里平台上配置的时候一定要注意，请严格按照变量在文本中出现的顺序进行编号，编号从0开始。
    !!! ATTENTION !!!

    SmsChannel:Primary = 单独使用网易，Backup = 单独使用阿里，TryBoth = 先使用网易，失败的话再使用阿里。
     */

    // 发送通道, 网易模板ID，网易模板，阿里模板ID，阿里模板
    // 发送的通道的优先级根据List的顺序 高 -> 低 决定，
    SMS_REGISTER_CAPTCHA_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "7430", "尊敬的拓天速贷客户，您的注册验证码是：%s 。请勿泄露给他人！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45345002", "尊敬的拓天速贷客户，您的注册验证码是：${param0} 。请勿泄露给他人！")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_NO_PASSWORD_INVEST_CAPTCHA_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "7431", "尊敬的拓天速贷客户，您的验证码是：%s 。请勿泄露给他人！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45395002", "尊敬的拓天速贷客户，您的验证码是：${param0} 。请勿泄露给他人！")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_MOBILE_CAPTCHA_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "6484", "尊敬的拓天速贷客户，您的找回密码的验证码是：%s 。请勿泄露给他人！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45370002", "尊敬的拓天速贷客户，您的找回密码的验证码是：${param0} 。请勿泄露给他人！")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_PASSWORD_CHANGED_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "6485", "尊敬的用户，您的登录密码已修改。如非本人操作，请速登录拓天速贷官网重置密码，或联系客服处理，电话：400-169-1188。"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45380002", "尊敬的用户，您的登录密码已修改。如非本人操作，请速登录拓天速贷官网重置密码，或联系客服处理，电话：400-169-1188。")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_LOAN_REPAY_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "6486", "您今天有 %s 元尚未还款，请及时登录系统还款。"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45420001", "您今天有 ${param0} 元尚未还款，请及时登录系统还款。")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_FATAL_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "8455", "%s 警报：%s"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45630075", "异常报告，环境：${param0}，错误码：${param1}。请尽快处理。")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_PLATFORM_BALANCE_LOW_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3032242", "系统账户余额不足 %s 元，请立刻充值！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45340002", "系统账户余额不足 ${param0} 元，请立刻充值！")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_EXPERIENCE_REPAY_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "8510", "亲爱的用户，您投资的新手体验项目所得%s元现金红包奖励已发放，快来激活奖励吧！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45415002", "亲爱的用户，您投资的新手体验项目所得${param0}元现金红包奖励已发放，快来激活奖励吧！")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_CANCEL_TRANSFER_LOAN(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3031027", "尊敬的拓天速贷客户，由于原始项目提前还款，您的债权转让项目%s已自动取消，请尽快登录平台进行查看。"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45340018", "尊敬的拓天速贷客户，由于原始项目提前还款，您的债权转让项目${param0}已自动取消，请尽快登录平台进行查看。")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_IMPORT_RECEIVE_MEMBERSHIP(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3031214", "尊敬的用户，拓天给您赠送了V%s会员，赶紧投资享受会员特权吧！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45360016", "尊敬的用户，拓天给您赠送了V${param0}会员，赶紧投资享受会员特权吧！")),
            Lists.newArrayList(SmsChannel.ALIDAYU)),

    SMS_NEW_USER_RECEIVE_MEMBERSHIP(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3031215", "尊敬的用户，欢迎您加入拓天，拓天给您赠送了V%s会员，赶紧投资享受会员特权吧！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45355032", "尊敬的用户，欢迎您加入拓天，拓天给您赠送了V${param0}会员，赶紧投资享受会员特权吧！")),
            Lists.newArrayList(SmsChannel.ALIDAYU)),

    SMS_GENERATE_CONTRACT_ERROR_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3034360", "安心签合同生成失败，标的/债权 id : %s"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45320005", "安心签合同生成失败，业务 id : ${param0}")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_LOAN_RAISING_COMPLETE_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3038299", "%s上线的%s天总额为%sw的项目于今日%s已满，%s;%s，30分钟内将完成复核。"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_45610002", "${param0}上线的${param1}天总额为${param2}w的项目于今日${param3}已满，客户:${param4};代理人:${param5}，30分钟内将完成复核。")),
            Lists.newArrayList(SmsChannel.NETEASE, SmsChannel.ALIDAYU)),

    SMS_COUPON_ASSIGN_SUCCESS_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3057070", "尊敬的用户，恭喜您获得了一张%s，请尽快使用拿奖励哦！"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_46830018", "尊敬的用户，恭喜您获得了一张${param0}，请尽快使用拿奖励哦！")),
            Lists.newArrayList(SmsChannel.ALIDAYU)),

    SMS_COUPON_EXPIRED_NOTIFY_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3050088", "尊敬的用户，您的%s即将过期（有效期至：%s），请尽快使用。"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_46865002", "尊敬的用户，您的${param0}即将过期（有效期至：${param1}），请尽快使用。")),
            Lists.newArrayList(SmsChannel.ALIDAYU)),

    SMS_CREDIT_LOAN_BALANCE_ALERT_TEMPLATE(Lists.newArrayList(
            new SmsTemplateCell(SmsChannel.NETEASE, "3118145", "信用贷标的账户余额不足，请及时登录速贷后台充值。"),
            new SmsTemplateCell(SmsChannel.ALIDAYU, "SMS_101445012", "慧租账户余额不足，请及时登录后台充值。")),
            Lists.newArrayList(SmsChannel.ALIDAYU));
    /*
    !!! ATTENTION !!!
    模板中的变量占位符在网易里使用的是 %s, 在阿里里使用的是 ${param0} ${param1}。

    请在阿里平台上配置的时候一定要注意，请严格按照变量在文本中出现的顺序进行编号，编号从0开始。
    请在阿里平台上配置的时候一定要注意，请严格按照变量在文本中出现的顺序进行编号，编号从0开始。
    请在阿里平台上配置的时候一定要注意，请严格按照变量在文本中出现的顺序进行编号，编号从0开始。
    !!! ATTENTION !!!
     */

    private List<SmsTemplateCell> smsTemplateCells;

    private List<SmsChannel> channelPriority;

    SmsTemplate(List<SmsTemplateCell> smsTemplateCells, List<SmsChannel> channelPriority) {
        this.smsTemplateCells = smsTemplateCells;
        this.channelPriority = channelPriority;
    }

    public List<SmsChannel> getChannelPriority() {
        return channelPriority;
    }

    public String getTemplateId(SmsChannel smsChannel) {
        Optional<SmsTemplateCell> optional = smsTemplateCells.stream().filter(smsTemplateCell -> smsTemplateCell.getSmsChannel() == smsChannel).findFirst();
        return optional.map(SmsTemplateCell::getTemplateId).orElse(null);
    }

    public String generateContent(List<String> templateParameters, SmsChannel smsChannel) {
        Optional<SmsTemplateCell> optional = smsTemplateCells.stream().filter(smsTemplateCell -> smsTemplateCell.getSmsChannel() == smsChannel).findFirst();

        if (!optional.isPresent()) {
            return "";
        }

        SmsTemplateCell smsTemplateCell = optional.get();
        if (Strings.isNullOrEmpty(smsTemplateCell.getTemplate()) || CollectionUtils.isEmpty(templateParameters)) {
            return smsTemplateCell.getTemplate();
        }

        return smsTemplateCell.getSmsChannel().getReplaceStrategy().replace(smsTemplateCell.getTemplate(), templateParameters);
    }
}
