package com.jinternals.event.bus.activemq.properties;

import com.jinternals.event.bus.common.configuration.Consumer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.activemq")
public class ActivemqEventBusConsumerProperties {
    @NestedConfigurationProperty
    private Consumer consumer;

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}

