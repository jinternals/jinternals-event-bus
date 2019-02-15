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

    private boolean exclusiveConsumer;

    @NestedConfigurationProperty
    private Producer producer;

    @NestedConfigurationProperty
    private Consumer consumer;

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

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public boolean isExclusiveConsumer() {
        return exclusiveConsumer;
    }

    public void setExclusiveConsumer(boolean exclusiveConsumer) {
        this.exclusiveConsumer = exclusiveConsumer;
    }
}
