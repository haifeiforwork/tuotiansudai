package com.tuotiansudai.mq.consumer.loan;

import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.mq.consumer.MessageConsumer;
import com.tuotiansudai.repository.mapper.CreditLoanBillMapper;
import com.tuotiansudai.repository.model.CreditLoanBillModel;
import com.tuotiansudai.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class CreditLoanBillMessageConsumer implements MessageConsumer {
    private static Logger logger = LoggerFactory.getLogger(CreditLoanBillMessageConsumer.class);

    @Autowired
    private CreditLoanBillMapper creditLoanBillMapper;

    @Override
    public MessageQueue queue() {
        return MessageQueue.CreditLoanBill;
    }

    @Override
    public void consume(String message) {
        logger.info("[MQ] receive message: {}: {}.", this.queue(), message);
        if (!StringUtils.isEmpty(message)) {
            CreditLoanBillModel billModel;
            try {
                billModel = JsonConverter.readValue(message, CreditLoanBillModel.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            logger.info("[MQ] ready to consume message to save credit loan bill. message:{}", message);
            creditLoanBillMapper.create(billModel);
            logger.info("[MQ] consume message success.");
        }
    }
}
