package com.jinternals.event.bus.activemq.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivemqTransactionManagerConfiguration {


    @ConditionalOnMissingBean
    @Bean("activemqConsumerTransactionManager")
    public PlatformTransactionManager activemqConsumerTransactionManager(
            @Qualifier("activemqJmsTransactionManager") PlatformTransactionManager activemqJmsTransactionManager) {
        return activemqJmsTransactionManager;
    }


}
