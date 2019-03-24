package com.jinternals.event.bus.rabbitmq.configuration;


import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.Objects;

import static java.util.Objects.nonNull;

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
            RabbitmqEventBusProperties rabbitmqEventBusProperties, @Value("${spring.application.name}")String name) {
        CachingConnectionFactory connectionFactory= new CachingConnectionFactory();
        connectionFactory.setUri(rabbitmqEventBusProperties.getBroker().getUrl());
        connectionFactory.setUsername(rabbitmqEventBusProperties.getBroker().getUserName());
        connectionFactory.setPassword(rabbitmqEventBusProperties.getBroker().getPassword());
        if(nonNull(name))
        {
            connectionFactory.setConnectionNameStrategy(conn ->name);
        }

        return connectionFactory;

    }

}
