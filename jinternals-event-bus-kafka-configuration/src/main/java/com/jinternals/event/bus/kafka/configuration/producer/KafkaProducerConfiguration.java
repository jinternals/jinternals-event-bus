package com.jinternals.event.bus.kafka.configuration.producer;

import com.jinternals.event.bus.kafka.gateway.KafkaEventGateway;
import com.jinternals.event.bus.kafka.properties.KafkaEventBusProducerProperties;
import com.jinternals.event.bus.kafka.properties.KafkaEventBusProperties;
import com.jinternals.event.bus.producer.gateway.EventGateway;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaProducerConfiguration {

    @Bean("kafkaProducerFactory")
    public ProducerFactory<String, Object> producerFactory(KafkaEventBusProperties kafkaEventBusProperties) {
        return new DefaultKafkaProducerFactory<>(producerConfiguration(kafkaEventBusProperties));
    }

    public Map<String, Object> producerConfiguration(KafkaEventBusProperties kafkaEventBusProperties) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaEventBusProperties.getBroker().getUrl());
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return properties;
    }


    @Bean("kafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate(
            @Qualifier("kafkaEventBusMessageConverter") StringJsonMessageConverter stringJsonMessageConverter,
            @Qualifier("kafkaProducerFactory") ProducerFactory<String, Object> producerFactory,
            KafkaEventBusProducerProperties kafkaEventBusProducerProperties) {

        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<String, Object>(producerFactory);
        kafkaTemplate.setMessageConverter(stringJsonMessageConverter);
        kafkaTemplate.setDefaultTopic(kafkaEventBusProducerProperties.getProducer().getDestination());
        return kafkaTemplate;
    }


    @Bean
    public EventGateway kafkaEventGateway(
            @Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventGateway(kafkaTemplate);
    }
}
