package com.jinternals.event.bus.common.configuration;


public class Producer {

    private Boolean enabled;

    private String destination;

    private Boolean isPubSub;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean getPubSub() {
        return isPubSub;
    }

    public void setPubSub(Boolean pubSub) {
        isPubSub = pubSub;
    }
}
