package com.jinternals.event.bus.activemq.configuration;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusConsumerProperties;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

import static org.springframework.jms.listener.DefaultMessageListenerContainer.CACHE_CONSUMER;

@Configuration
@ConditionalOnProperty(prefix = "event.bus.activemq.consumer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActivemqConsumerConfiguration {

    @Bean("activemqMessageProducerSupport")
    public MessageProducerSupport activemqMessageProducerSupport(
            @Qualifier("activemqPooledConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier("activemqConsumerTransactionManager") PlatformTransactionManager transactionManager,
            @Qualifier("activemqMessageConverter") MessageConverter messageConverter,
            ActivemqEventBusConsumerProperties properties) {
        return Jms
                .messageDrivenChannelAdapter(connectionFactory)
                .configureListenerContainer(spec -> setup(spec.get(), transactionManager, messageConverter, properties))
                .destination(properties.getConsumer().getDestination())
                .jmsMessageConverter(messageConverter)
                .autoStartup(true)
                .get();
    }

    public void setup(DefaultMessageListenerContainer container,
                      PlatformTransactionManager transactionManager,
                      MessageConverter messageConverter,
                      ActivemqEventBusConsumerProperties activemqEventBusProperties) {
        container.setSessionTransacted(true);
        container.setCacheLevel(CACHE_CONSUMER);
        container.setConcurrency(activemqEventBusProperties.getConsumer().getConcurrency());
        container.setMessageConverter(messageConverter);
        container.setTransactionManager(transactionManager);
    }


}
