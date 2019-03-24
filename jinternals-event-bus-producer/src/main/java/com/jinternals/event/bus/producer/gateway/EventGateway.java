package com.jinternals.event.bus.producer.gateway;

import java.util.Map;

public interface EventGateway {

    public final static String EVENT_HEADER_TYPE = "type";
    public final static String EVENT_HEADER_EVENT_ID = "event_id";
    public final static String EVENT_HEADER_ROUTING_KEY = "routing_key";

    void publish(Object event);

    void publish(Object event, Map<String, Object> headers);



}
