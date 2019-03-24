package com.jinternals.event.bus.activemq.properties;

import com.jinternals.event.bus.common.configuration.Broker;
import com.jinternals.event.bus.common.configuration.Consumer;
import com.jinternals.event.bus.common.configuration.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "event.bus.activemq")
public class ActivemqEventBusProperties {

    public ActivemqEventBusProperties() {
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
