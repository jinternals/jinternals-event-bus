package com.jinternals.event.bus.activemq.properties;

import com.jinternals.event.bus.common.configuration.Consumer;
import com.jinternals.event.bus.common.configuration.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.activemq")
public class ActivemqEventBusProducerProperties {
    @NestedConfigurationProperty
    private Producer producer;

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}

