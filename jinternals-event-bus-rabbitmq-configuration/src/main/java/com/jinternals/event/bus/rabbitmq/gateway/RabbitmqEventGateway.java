package com.jinternals.event.bus.rabbitmq.gateway;

import com.jinternals.event.bus.common.EventBusConstants;
import com.jinternals.event.bus.producer.gateway.AbstractEventGateway;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;

import java.util.Map;
import java.util.UUID;

import static com.jinternals.event.bus.common.EventBusConstants.EVENT_HEADER_EVENT_ID;


public class RabbitmqEventGateway extends AbstractEventGateway {

    private AmqpTemplate amqpTemplate;

    public RabbitmqEventGateway(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void publish(Object event) {
        amqpTemplate.convertAndSend(event, message -> {
            setMessagePropertiesAndHeaders(event, message);
            return message;
        });
    }

    @Override
    public void publish(Object event, Map<String, Object> headers) {
        amqpTemplate.convertAndSend(event, message -> {


            setMessagePropertiesAndHeaders(event, message);

            headers.entrySet().stream().forEach(header -> {
                message.getMessageProperties().setHeader(header.getKey(), header.getValue());
            });

            return message;
        });
    }

    private void setMessagePropertiesAndHeaders(Object event, Message message) {
        getHeaders(event)
                .entrySet()
                .stream()
                .forEach(header ->  message.getMessageProperties().setHeader(header.getKey(),header.getValue()));

        message.getMessageProperties().setMessageId((String) getHeaders(event).get(EventBusConstants.EVENT_HEADER_EVENT_ID));
    }

}
