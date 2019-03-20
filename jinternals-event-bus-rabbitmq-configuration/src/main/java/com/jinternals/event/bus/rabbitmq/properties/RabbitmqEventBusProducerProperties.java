package com.jinternals.event.bus.rabbitmq.properties;

import com.jinternals.event.bus.common.configuration.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.rabbitmq")
public class RabbitmqEventBusProducerProperties {
    @NestedConfigurationProperty
    private Producer producer;

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}

