package com.jinternals.event.bus.common.configuration;


public class Producer {

    private boolean enabled;

    private String destination;

    private boolean isPubSub;

    private boolean orderedDelivery;

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean getPubSub() {
        return isPubSub;
    }

    public void setPubSub(boolean pubSub) {
        isPubSub = pubSub;
    }

    public boolean getOrderedDelivery() {
        return orderedDelivery;
    }

    public void setOrderedDelivery(boolean orderedDelivery) {
        this.orderedDelivery = orderedDelivery;
    }
}
