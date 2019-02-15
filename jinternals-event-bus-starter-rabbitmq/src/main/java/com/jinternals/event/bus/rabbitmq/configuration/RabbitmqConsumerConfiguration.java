package com.jinternals.event.bus.rabbitmq.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnProperty(prefix = "event.bus.rabbitmq.consumer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RabbitmqConsumerConfiguration {

    @Bean("rabbitmqMessageProducerSupport")
    public MessageProducerSupport rabbitmqMessageProducerSupport(
            @Qualifier("rabbitmqSimpleMessageListenerContainer")  SimpleMessageListenerContainer rabbitmqSimpleMessageListenerContainer,
     @Qualifier("rabbitmqEventBusMessageConverter") MessageConverter rabbitmqEventBusMessageConverter
    ) {
        return Amqp
                .inboundAdapter(rabbitmqSimpleMessageListenerContainer)
                .messageConverter(rabbitmqEventBusMessageConverter)
                .autoStartup(true)
                .get();
    }

    @Bean("rabbitmqSimpleMessageListenerContainer")
    public SimpleMessageListenerContainer rabbitmqSimpleMessageListenerContainer(
            @Qualifier("rabbitmqCachingConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier("rabbitmqTransactionManager") PlatformTransactionManager transactionManager,
            @Qualifier("rabbitmqEventBusMessageConverter") MessageConverter rabbitmqEventBusMessageConverter,
            RabbitmqEventBusProperties rabbitmqEventBusProperties) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTransactionManager(transactionManager);
        container.setChannelTransacted(true);
        container.setQueueNames(rabbitmqEventBusProperties.getConsumer().getDestination());
        container.setConcurrency(rabbitmqEventBusProperties.getConsumer().getConcurrency());
        //container.setMessageConverter(rabbitmqEventBusMessageConverter);
        return container;
    }


}
