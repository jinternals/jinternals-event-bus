package com.jinternals.event.bus.rabbitmq.configuration;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitmqBrokerConfiguration {

    @ConditionalOnProperty(prefix = "event.bus.rabbitmq.consumer", name = "enabled", havingValue = "true", matchIfMissing = true)

    @Bean("rabbitmqTransactionManager")
    public PlatformTransactionManager rabbitmqTransactionManager(
            @Qualifier("rabbitmqCachingConnectionFactory") ConnectionFactory cachingConnectionFactory) {
        RabbitTransactionManager transactionManager = new RabbitTransactionManager();
        transactionManager.setConnectionFactory(cachingConnectionFactory);
        return transactionManager;
    }

    @Bean("rabbitmqCachingConnectionFactory")
    public ConnectionFactory rabbitmqCachingConnectionFactory(
            RabbitmqEventBusProperties rabbitmqEventBusProperties) {
        CachingConnectionFactory connectionFactory= new CachingConnectionFactory();
        connectionFactory.setUri(rabbitmqEventBusProperties.getBroker().getUrl());
        connectionFactory.setUsername(rabbitmqEventBusProperties.getBroker().getUserName());
        connectionFactory.setPassword(rabbitmqEventBusProperties.getBroker().getPassword());
        return connectionFactory;

    }

}
