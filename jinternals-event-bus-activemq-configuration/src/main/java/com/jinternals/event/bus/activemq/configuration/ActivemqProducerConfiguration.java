package com.jinternals.event.bus.activemq.configuration;


import com.jinternals.event.bus.activemq.gateway.ActivemqEventGateway;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusConsumerProperties;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProducerProperties;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;
import com.jinternals.event.bus.producer.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.Session;


@Configuration
@ConditionalOnProperty(prefix = "event.bus.activemq.producer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActivemqProducerConfiguration {

    @Bean("activemqJmsTemplate")
    public JmsTemplate activemqJmsTemplate(
            @Qualifier("activemqPooledConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier("activemqMessageConverter") MessageConverter messageConverter,
            ActivemqEventBusProducerProperties activemqEventBusProducerProperties) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestinationName(activemqEventBusProducerProperties.getProducer().getDestination());
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setPubSubDomain(activemqEventBusProducerProperties.getProducer().getPubSub());
        return jmsTemplate;
    }

    @Bean
    public EventGateway activemqEventGateway(
            @Qualifier("activemqJmsTemplate") JmsTemplate jmsTemplate,
            ActivemqEventBusProducerProperties activemqEventBusProducerProperties) {
        return new ActivemqEventGateway(jmsTemplate, activemqEventBusProducerProperties);
    }
}
