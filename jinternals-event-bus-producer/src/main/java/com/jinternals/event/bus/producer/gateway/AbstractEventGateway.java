package com.jinternals.event.bus.producer.gateway;

import com.jinternals.event.bus.producer.gateway.annotation.EventRoutingId;
import com.jinternals.event.bus.producer.gateway.utils.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractEventGateway implements EventGateway {

    protected Map<String, String> getHeaders(Object event) {
        Map<String, String> headers = new HashMap<>();
        headers.put(EVENT_HEADER_TYPE, event.getClass().getName());
        headers.put(EVENT_HEADER_EVENT_ID, UUID.randomUUID().toString());
        headers.put(EVENT_HEADER_ROUTING_KEY, (String) getEventRoutingKey(event, EventRoutingId.class));
        return headers;
    }

    private Object getEventRoutingKey(Object event, Class annotation) {
        List<Field> annotatedFields = FieldUtils.getAnnotatedFields(event.getClass(), annotation);

        Field field = annotatedFields.stream().findFirst().orElse(null);
        if(Objects.nonNull(field)){
           return ReflectionUtils.getField(field,event);
        }
        return null;

    }
}

