package com.jinternals.event.bus.kafka.configuration;

import com.jinternals.event.bus.common.headers.EventHeaders;
import com.jinternals.event.bus.common.utils.EventUtils;
import com.jinternals.event.bus.kafka.properties.KafkaEventBusProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.Map;

import static org.springframework.kafka.support.converter.Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID;

public class KafkaMessageConverterConfiguration {


    @Bean("kafkaEventBusMessageConverter")
    public StringJsonMessageConverter stringJsonMessageConverter(
            @Qualifier("kafkaJackson2JavaTypeMapper") Jackson2JavaTypeMapper jackson2JavaTypeMapper) {
        StringJsonMessageConverter stringJsonMessageConverter = new StringJsonMessageConverter();

        stringJsonMessageConverter.setTypeMapper(jackson2JavaTypeMapper);
        return stringJsonMessageConverter;
    }


    @Bean("kafkaJackson2JavaTypeMapper")
    public Jackson2JavaTypeMapper jackson2JavaTypeMapper(
            @Qualifier("kafkaEventMappings") Map<String, Class<?>> eventMappings) {
        DefaultJackson2JavaTypeMapper classMapper = new DefaultJackson2JavaTypeMapper() {
            @Override
            public String getClassIdFieldName() {
                return EventHeaders.EVENT_HEADER_TYPE;
            }

        };
        classMapper.setTypePrecedence(TYPE_ID);
        classMapper.setIdClassMapping(eventMappings);
        return classMapper;
    }

    @Bean("kafkaEventMappings")
    public Map<String, Class<?>> getTypeMapping(KafkaEventBusProperties kafkaEventBusProperties) {
        return EventUtils.getTypeMapping(kafkaEventBusProperties.getEventPackage());
    }

}
