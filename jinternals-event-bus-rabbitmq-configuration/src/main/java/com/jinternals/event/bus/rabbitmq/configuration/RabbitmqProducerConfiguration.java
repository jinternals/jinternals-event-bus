package com.jinternals.event.bus.rabbitmq.configuration;


import com.jinternals.event.bus.producer.gateway.EventGateway;
import com.jinternals.event.bus.rabbitmq.gateway.RabbitmqEventGateway;
import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProducerProperties;
import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(prefix = "event.bus.rabbitmq.producer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RabbitmqProducerConfiguration {

    @Bean("rabbitmqTemplate")
    public AmqpTemplate rabbitmqTemplate(
            @Qualifier("rabbitmqCachingConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier("rabbitmqEventBusMessageConverter") MessageConverter messageConverter,
            RabbitmqEventBusProducerProperties rabbitmqEventBusProducerProperties) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(rabbitmqEventBusProducerProperties.getProducer().getDestination());
        return rabbitTemplate;
    }

    @Bean
    public EventGateway activemqEventGateway(
            @Qualifier("rabbitmqTemplate") AmqpTemplate amqpTemplate) {
        return new RabbitmqEventGateway(amqpTemplate);
    }
}
