package com.jinternals.event.bus.activemq.gateway;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;
import com.jinternals.event.bus.common.annotations.EventId;
import com.jinternals.event.bus.producer.gateway.EventGateway;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.ReflectionUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;

import static com.jinternals.event.bus.common.EventBusConstants.MESSAGE_HEADER_TYPE;

public class ActivemqEventGateway implements EventGateway {

    private JmsTemplate template;
    private ActivemqEventBusProperties activemqEventBusProperties;

    public ActivemqEventGateway(JmsTemplate template, ActivemqEventBusProperties activemqEventBusProperties) {
        this.template = template;
        this.activemqEventBusProperties = activemqEventBusProperties;
    }

    @Override
    public void publish(Object event) {
        template.convertAndSend(event, message -> {
            setHeaders(event, message);
            return message;
        });
    }


    @Override
    public void publish(Object event, Map<String, Object> headers) {
        template.convertAndSend(event, message -> {


            for (Map.Entry<String, Object> header : headers.entrySet()) {
                message.setObjectProperty(header.getKey(), header.getValue());
            }

            setHeaders(event, message);
            return message;
        });
    }

    private void setHeaders(Object event, Message message) throws JMSException {
        if (activemqEventBusProperties.isExclusiveConsumer()) {

            ReflectionUtils.doWithFields(event.getClass(), field -> {

                if (field.isAnnotationPresent(EventId.class)) {
                    field.setAccessible(true);
                    Object value = ReflectionUtils.getField(field, event);
                    try {
                        message.setObjectProperty("JMSXGroupID", value);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
        message.setStringProperty(MESSAGE_HEADER_TYPE, event.getClass().getName());
    }

}
