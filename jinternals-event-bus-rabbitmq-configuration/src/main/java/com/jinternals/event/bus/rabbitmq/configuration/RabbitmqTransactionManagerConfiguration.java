package com.jinternals.event.bus.rabbitmq.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class RabbitmqTransactionManagerConfiguration {


    @ConditionalOnMissingBean
    @Bean("rabbitmqConsumerTransactionManager")
    public PlatformTransactionManager activemqConsumerTransactionManager(
            @Qualifier("rabbitmqTransactionManager") PlatformTransactionManager activemqJmsTransactionManager) {
        return activemqJmsTransactionManager;
    }


}
