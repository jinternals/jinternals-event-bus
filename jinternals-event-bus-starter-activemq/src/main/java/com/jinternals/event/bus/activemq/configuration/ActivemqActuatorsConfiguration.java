package com.jinternals.event.bus.activemq.configuration;

import com.google.common.collect.Maps;
import com.jinternals.event.bus.actuators.EventMappingEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ActivemqActuatorsConfiguration {


    @Bean("activemqEventMappingEndpoint")
    public ActivemqEventMappingEndpoint activemqEventMappingEndpoint() {
        return new ActivemqEventMappingEndpoint();
    }

    @Endpoint(id = "activemq-event-mapping")
    public static class ActivemqEventMappingEndpoint  {

        @Autowired
        @Qualifier("activemqEventMappings")
        private Map<String, Class<?>> eventMappings;

        @ReadOperation
        public Map<String, Class<?>> getTypeMapping() {
            return this.eventMappings;
        }
    }

}
