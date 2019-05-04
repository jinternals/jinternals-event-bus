package com.jinternals.event.bus.kafka.properties;

import com.jinternals.event.bus.common.configuration.Broker;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.kafka")
public class KafkaEventBusProperties {

    @NestedConfigurationProperty
    private Broker broker;

    private String eventPackage;

    public String getEventPackage() {
        return eventPackage;
    }

    public void setEventPackage(String eventPackage) {
        this.eventPackage = eventPackage;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }
}
