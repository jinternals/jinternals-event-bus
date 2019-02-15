package com.jinternals.event.bus.activemq.configuration;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;
import com.jinternals.event.bus.common.utils.EventUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Map;

@Configuration
public class ActivemqMessageConverterConfiguration {

    @Bean("activemqMessageConverter")
    public MappingJackson2MessageConverter activemqEventBusMessageConverter(
            @Qualifier("activemqEventMappings") Map<String, Class<?>> eventMappings) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("type");
        converter.setTypeIdMappings(eventMappings);
        return converter;
    }


    @Bean("activemqEventMappings")
    public Map<String, Class<?>> activemqEventMappings(ActivemqEventBusProperties activemqEventBusProperties) {
        return EventUtils.getTypeMapping(activemqEventBusProperties.getEventPackage());

    }

}
