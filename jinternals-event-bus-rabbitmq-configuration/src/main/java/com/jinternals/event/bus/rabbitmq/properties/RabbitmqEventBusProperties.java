package com.jinternals.event.bus.rabbitmq.properties;

import com.jinternals.event.bus.common.configuration.Broker;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.rabbitmq")
public class RabbitmqEventBusProperties {

    public RabbitmqEventBusProperties() {
    }

    @NestedConfigurationProperty
    private Broker broker;

    private String listenerPackage;

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

    public String getListenerPackage() {
        return listenerPackage;
    }

    public void setListenerPackage(String listenerPackage) {
        this.listenerPackage = listenerPackage;
    }

}
