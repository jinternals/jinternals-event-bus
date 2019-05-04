package com.jinternals.event.bus.activemq.gateway;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProducerProperties;
import com.jinternals.event.bus.common.headers.EventHeaders;
import com.jinternals.event.bus.producer.gateway.AbstractEventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;

import static java.util.Objects.nonNull;

public class ActivemqEventGateway extends AbstractEventGateway {

    private static Logger logger = LoggerFactory.getLogger(ActivemqEventGateway.class);

    private JmsTemplate template;
    private ActivemqEventBusProducerProperties activemqEventBusProducerProperties;

    public ActivemqEventGateway(JmsTemplate template, ActivemqEventBusProducerProperties activemqEventBusProducerProperties) {
        this.template = template;
        this.activemqEventBusProducerProperties = activemqEventBusProducerProperties;
    }

    @Override
    public void publish(Object event) {
        template.convertAndSend(event, message -> {
            setCommonPropertyAndHeaders(message, event);
            return message;
        });
    }


    @Override
    public void publish(Object event, Map<String, Object> headers) {
        template.convertAndSend(event, message -> {
            setCommonPropertyAndHeaders(message, event);

            headers
                    .entrySet()
                    .stream()
                    .forEach(header -> {
                        try {
                            message.setObjectProperty(header.getKey(), header.getValue());
                        } catch (JMSException exception) {
                            logger.error(exception.getMessage(), exception);
                        }
                    });

            return message;
        });
    }


    private void setCommonPropertyAndHeaders(Message message, Object event) throws JMSException {

        getHeaders(event)
                .entrySet()
                .stream()
                .forEach(header -> setHeader(message, header));


        if (activemqEventBusProducerProperties.getProducer().getOrderedDelivery()) {
            Object routingKey = getHeaders(event).get(EventHeaders.EVENT_HEADER_ROUTING_KEY);
            if (nonNull(routingKey)) {
                message.setObjectProperty("JMSXGroupID", routingKey);
            }
        }

    }

    private void setHeader(Message message, Map.Entry<String, String> header) {
        try {
            message.setStringProperty(header.getKey(), header.getValue());
        } catch (JMSException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }


}
