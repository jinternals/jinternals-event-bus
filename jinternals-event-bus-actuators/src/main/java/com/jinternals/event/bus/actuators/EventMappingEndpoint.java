package com.jinternals.event.bus.actuators;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.Map;

public class EventMappingEndpoint {

    private Map<String, Class<?>> typeMapping;

    public EventMappingEndpoint(Map<String, Class<?>> typeMapping) {
        this.typeMapping = typeMapping;
    }

    @ReadOperation
    public Map<String, Class<?>> getTypeMapping() {
        return this.typeMapping;
    }

}
