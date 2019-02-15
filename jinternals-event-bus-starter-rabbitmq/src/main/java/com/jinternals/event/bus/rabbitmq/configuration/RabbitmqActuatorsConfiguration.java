package com.jinternals.event.bus.rabbitmq.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitmqActuatorsConfiguration {


    @Bean("rabbitmqEventMappingEndpoint")
    public RabbitmqEventMappingEndpoint activemqEventMappingEndpoint() {
        return new RabbitmqEventMappingEndpoint();
    }

    @Endpoint(id = "rabbitmq-event-mapping")
    public static class RabbitmqEventMappingEndpoint {

        @Autowired
        @Qualifier("rabbitmqEventMappings")
        private Map<String, Class<?>> eventMappings;

        @ReadOperation
        public Map<String, Class<?>> getTypeMapping() {
            return this.eventMappings;
        }
    }

}
