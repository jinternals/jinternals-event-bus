package com.jinternals.event.bus.rabbitmq.configuration;

import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusConsumerProperties;
import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProperties;
import org.springframework.amqp.core.AcknowledgeMode;
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
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import static org.springframework.amqp.core.AcknowledgeMode.MANUAL;

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
            @Qualifier("rabbitmqConsumerTransactionManager") PlatformTransactionManager transactionManager,
            @Qualifier("rabbitmqEventBusMessageConverter") MessageConverter rabbitmqEventBusMessageConverter,
            RabbitmqEventBusConsumerProperties rabbitmqEventBusConsumerProperties) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTransactionManager(transactionManager);
        container.setChannelTransacted(true);
        container.setQueueNames(rabbitmqEventBusConsumerProperties.getConsumer().getDestination());
        container.setConcurrency(rabbitmqEventBusConsumerProperties.getConsumer().getConcurrency());
        container.setAcknowledgeMode(MANUAL);
        container.setPrefetchCount(1);
        container.setDefaultRequeueRejected(true);
        BackOff recoveryBackOff = new FixedBackOff(5000, 3);
        container.setRecoveryBackOff(recoveryBackOff);

        return container;
    }


}
