package com.jinternals.event.bus.rabbitmq.gateway;

import com.jinternals.event.bus.producer.gateway.EventGateway;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Map;

import static com.jinternals.event.bus.common.EventBusConstants.MESSAGE_HEADER_TYPE;

public class RabbitmqEventGateway implements EventGateway {

    private AmqpTemplate amqpTemplate;

    public RabbitmqEventGateway(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void publish(Object event) {
        amqpTemplate.convertAndSend(event, message -> {
            message.getMessageProperties().setHeader(MESSAGE_HEADER_TYPE, event.getClass().getName());
            return message;
        });
    }

    @Override
    public void publish(Object event, Map<String, Object> headers) {
        amqpTemplate.convertAndSend(event, message -> {

            headers.entrySet().stream().forEach(header -> {
                message.getMessageProperties().setHeader(header.getKey(), header.getValue());
            });

            message.getMessageProperties().setHeader(MESSAGE_HEADER_TYPE, event.getClass().getName());
            return message;
        });
    }
}
