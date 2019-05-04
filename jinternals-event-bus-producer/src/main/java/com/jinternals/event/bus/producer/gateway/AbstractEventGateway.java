package com.jinternals.event.bus.producer.gateway;

import com.jinternals.event.bus.common.headers.EventHeaders;
import com.jinternals.event.bus.producer.gateway.annotation.EventRoutingId;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.jinternals.event.bus.producer.gateway.utils.FieldUtils.getAnnotatedFields;
import static java.util.Objects.nonNull;
import static org.springframework.util.ReflectionUtils.getField;

public abstract class AbstractEventGateway implements EventGateway {

    protected Map<String, String> getHeaders(Object event) {
        Map<String, String> headers = new HashMap<>();
        headers.put(EventHeaders.EVENT_HEADER_TYPE, event.getClass().getName());
        headers.put(EventHeaders.EVENT_HEADER_EVENT_ID, UUID.randomUUID().toString());
        headers.put(EventHeaders.EVENT_HEADER_ROUTING_KEY, (String) getEventRoutingKey(event, EventRoutingId.class));
        return headers;
    }

    private Object getEventRoutingKey(Object event, Class annotation) {
        List<Field> annotatedFields = getAnnotatedFields(event.getClass(), annotation);

        Field field = annotatedFields.stream().findFirst().orElse(null);

        if (nonNull(field)) {
            return getField(field, event);
        }

        return null;

    }
}

