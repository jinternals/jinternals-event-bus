package com.jinternals.event.bus.rabbitmq.configuration;

import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitmqMessageConverterConfiguration {

    @Bean("rabbitmqEventBusMessageConverter")
    public MessageConverter rabbitmqEventBusMessageConverter(
            @Qualifier("rabbitmqDefaultClassMapper") DefaultJackson2JavaTypeMapper defaultClassMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        converter.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        converter.setClassMapper(defaultClassMapper);
        return converter;
    }

    @Bean("rabbitmqDefaultClassMapper")
    public DefaultJackson2JavaTypeMapper classMapper(
            @Qualifier("rabbitmqEventMappings") Map<String, Class<?>> eventMappings) {
        DefaultJackson2JavaTypeMapper classMapper = new DefaultJackson2JavaTypeMapper(){
            @Override
            public String getClassIdFieldName() {
                return "type";
            }

        };
        classMapper.setIdClassMapping(eventMappings);
        return classMapper;
    }

}
