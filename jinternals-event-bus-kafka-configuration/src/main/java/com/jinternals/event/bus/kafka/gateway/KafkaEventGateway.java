package com.jinternals.event.bus.kafka.gateway;

import com.jinternals.event.bus.common.headers.EventHeaders;
import com.jinternals.event.bus.producer.gateway.AbstractEventGateway;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

public class KafkaEventGateway extends AbstractEventGateway {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventGateway(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(Object event) {

        MessageBuilder<Object> eventMessageBuilder = MessageBuilder
                .withPayload(event);

        setMessagePropertiesAndHeaders(event, eventMessageBuilder);

        kafkaTemplate.send(eventMessageBuilder.build());
    }

    @Override
    public void publish(Object event, Map<String, Object> headers) {
        MessageBuilder<Object> eventMessageBuilder = MessageBuilder
                .withPayload(event);

        setMessagePropertiesAndHeaders(event, eventMessageBuilder);

        headers.entrySet().stream().forEach(header -> {
            eventMessageBuilder.setHeader(header.getKey(), header.getValue());
        });

        kafkaTemplate.send(eventMessageBuilder.build());

    }

    private void setMessagePropertiesAndHeaders(Object event, MessageBuilder<Object> message) {

        message.copyHeaders(getHeaders(event));

        message.setHeader(KafkaHeaders.MESSAGE_KEY, getHeaders(event).get(EventHeaders.EVENT_HEADER_EVENT_ID));
    }

}
