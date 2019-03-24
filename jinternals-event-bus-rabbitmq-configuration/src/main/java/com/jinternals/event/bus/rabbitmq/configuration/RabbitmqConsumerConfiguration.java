package com.jinternals.event.bus.rabbitmq.configuration;

import com.jinternals.event.bus.common.configuration.Retry;
import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusConsumerProperties;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.amqp.core.AcknowledgeMode.MANUAL;

@Configuration
@ConditionalOnProperty(prefix = "event.bus.rabbitmq.consumer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RabbitmqConsumerConfiguration {

    @Bean("rabbitmqMessageProducerSupport")
    public MessageProducerSupport rabbitmqMessageProducerSupport(
            @Qualifier("rabbitmqSimpleMessageListenerContainer") SimpleMessageListenerContainer rabbitmqSimpleMessageListenerContainer,
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

        Retry retry = rabbitmqEventBusConsumerProperties.getConsumer().getRetry();
        if (nonNull(retry) && retry.getEnabled()) {
            container.setAdviceChain(retryOperationsInterceptor(retry));
        }

        return container;
    }

    /*
        https://github.com/spring-cloud/spring-cloud-stream/issues/534#issuecomment-219230564
     */
    @Bean
    @ConditionalOnProperty(prefix = "event.bus.rabbitmq.consumer.retry",
            name = "enabled", havingValue = "true",
            matchIfMissing = false)
    public StatefulRetryOperationsInterceptor retryOperationsInterceptor(Retry retry) {

        return RetryInterceptorBuilder
                .stateful()
                .maxAttempts(retry.getMaxAttempts())
                .backOffOptions(
                        retry.getInitialInterval(),
                        retry.getMultiplier(),
                        retry.getMaxInterval()
                )
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }
}
