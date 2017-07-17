package com.tuotiansudai.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuotiansudai.dto.Environment;
import com.tuotiansudai.mq.client.MQProducer;
import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.mq.client.model.MessageTopic;
import com.tuotiansudai.util.JsonConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

@Component
public class MQWrapperClient {

    private static Logger logger = Logger.getLogger(MQWrapperClient.class);

    @Autowired
    private MQProducer mqProducer;

    @Value("${common.environment}")
    private Environment env;

    public void publishMessage(final MessageTopic topic, Object message) throws JsonProcessingException {
        String messageString = JsonConverter.writeValueAsString(message);
        runAfterCommit(() -> mqProducer.publishMessage(topic, messageString));
    }

    public void sendMessage(final MessageQueue queue, final String message) {
        if (env == Environment.UT || env == Environment.DEV) {
            mqProducer.sendMessage(queue, message);
        } else {
            runAfterCommit(() -> mqProducer.sendMessage(queue, message));
        }
    }

    public void sendMessage(final MessageQueue queue, final Object message) {
        if (env == Environment.UT || env == Environment.DEV) {
            try {
                mqProducer.sendMessage(queue, JsonConverter.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        } else {
            runAfterCommit(() -> {
                try {
                    mqProducer.sendMessage(queue, JsonConverter.writeValueAsString(message));
                } catch (JsonProcessingException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            });
        }
    }

    private void runAfterCommit(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }
}
