package com.jinternals.event.bus.rabbitmq.properties;

import com.jinternals.event.bus.common.configuration.Consumer;
import com.jinternals.event.bus.common.configuration.Retry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.rabbitmq")
public class RabbitmqEventBusConsumerProperties {
    @NestedConfigurationProperty
    private Consumer consumer;

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

}

