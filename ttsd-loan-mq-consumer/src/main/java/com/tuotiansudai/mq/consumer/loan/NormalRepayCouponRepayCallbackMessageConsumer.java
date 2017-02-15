package com.tuotiansudai.mq.consumer.loan;

import com.google.common.base.Strings;
import com.tuotiansudai.client.PayWrapperClient;
import com.tuotiansudai.client.SmsWrapperClient;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.sms.SmsFatalNotifyDto;
import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.mq.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class NormalRepayCouponRepayCallbackMessageConsumer implements MessageConsumer {
    private static Logger logger = LoggerFactory.getLogger(NormalRepayCouponRepayCallbackMessageConsumer.class);

    @Autowired
    private PayWrapperClient payWrapperClient;
    @Autowired
    private SmsWrapperClient smsWrapperClient;

    @Override
    public MessageQueue queue() {
        return MessageQueue.NormalRepayCouponRepayCallback;
    }

    @Transactional
    @Override
    public void consume(String message) {
        logger.info("[还款优惠券收益回调MQ] receive message: {}: {}.", this.queue(), message);
        if (Strings.isNullOrEmpty(message)) {
            logger.error("[还款优惠券收益回调MQ] ready to consume message: normal repay coupon repay message is null.");
            smsWrapperClient.sendFatalNotify(new SmsFatalNotifyDto("还款优惠券收益回调MQ消息为空!"));
            return;
        }
        logger.info("[还款优惠券收益回调MQ] ready to consume message: normal repay coupon repay callback.");
        BaseDto<PayDataDto> result = payWrapperClient.normalRepayInvestPayback(Long.parseLong(message));
        if (!result.isSuccess()) {
            logger.error("normal repay callback consume fail. notifyRequestId: " + message);
            throw new RuntimeException("normal repay callback consume fail. notifyRequestId: " + message);
        }
        logger.info("[MQ] consume message success.");
    }
}
