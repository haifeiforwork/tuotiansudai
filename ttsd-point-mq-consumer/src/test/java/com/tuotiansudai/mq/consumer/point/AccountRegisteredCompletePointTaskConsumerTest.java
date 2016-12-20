package com.tuotiansudai.mq.consumer.point;

import com.tuotiansudai.mq.consumer.MessageConsumer;
import com.tuotiansudai.point.repository.model.PointTask;
import com.tuotiansudai.point.service.PointTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountRegisteredCompletePointTaskConsumerTest extends PointTaskConsumerTestBase {

    @Autowired
    @Qualifier("accountRegisteredCompletePointTaskConsumer")
    private MessageConsumer consumer;

    @MockBean
    private PointTaskService pointTaskService;

    @Test
    @Transactional
    public void shouldConsume() {
        shouldCompleteNewbieTask(consumer, pointTaskService, PointTask.REGISTER);
    }
}
