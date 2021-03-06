package com.jinternals.event.bus.activemq.configuration;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivemqBrokerConfiguration {


    @Bean("activemqJmsTransactionManager")
    public PlatformTransactionManager activemqJmsTransactionManager(ActivemqEventBusProperties activemqEventBusProperties) {
        JmsTransactionManager transactionManager = new JmsTransactionManager();
        transactionManager.setConnectionFactory(activemqPooledConnectionFactory(activemqEventBusProperties));
        return transactionManager;
    }

    @Primary
    @Bean("activemqPooledConnectionFactory")
    public PooledConnectionFactory activemqPooledConnectionFactory(ActivemqEventBusProperties activemqEventBusProperties) {
        return new PooledConnectionFactory(activemqConnectionFactory(activemqEventBusProperties));
    }

    @Bean("activemqConnectionFactory")
    public ActiveMQConnectionFactory activemqConnectionFactory(ActivemqEventBusProperties activemqEventBusProperties) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activemqEventBusProperties.getBroker().getUrl());
        connectionFactory.setPassword(activemqEventBusProperties.getBroker().getUserName());
        connectionFactory.setUserName(activemqEventBusProperties.getBroker().getPassword());
        return connectionFactory;
    }
}
