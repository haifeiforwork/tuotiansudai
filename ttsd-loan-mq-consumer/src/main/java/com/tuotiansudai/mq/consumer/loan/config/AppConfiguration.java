package com.tuotiansudai.mq.consumer.loan.config;

import com.tuotiansudai.job.JobManager;
import com.tuotiansudai.quartz.JobStoreBuilder;
import com.tuotiansudai.quartz.SchedulerBuilder;
import com.tuotiansudai.rest.client.UserMapperConfiguration;
import com.tuotiansudai.spring.ETCDPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "com.tuotiansudai.service",
        "com.tuotiansudai.transfer",
        "com.tuotiansudai.util",
        "com.tuotiansudai.client",
        "com.tuotiansudai.coupon",
        "com.tuotiansudai.membership",
        "com.tuotiansudai.log"
})
@EnableAspectJAutoProxy(exposeProxy = true)
@Import(UserMapperConfiguration.class)
public class AppConfiguration {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new ETCDPropertyPlaceholderConfigurer();
    }

    @Bean
    public JobStoreBuilder jobStoreBuilder(@Qualifier("hikariCPJobDataSource") DataSource dataSource) {
        return new JobStoreBuilder(dataSource);
    }

    @Bean
    public SchedulerBuilder schedulerBuilder(JobStoreBuilder jobStoreBuilder) {
        return new SchedulerBuilder(jobStoreBuilder);
    }

    @Bean
    public JobManager jobManager(SchedulerBuilder schedulerBuilder) {
        return new JobManager(schedulerBuilder);
    }
}
