package com.jinternals.event.bus.producer.gateway;

import java.util.Map;

public interface EventGateway {

    void publish(Object event);

    void publish(Object event, Map<String, Object> headers);

}
