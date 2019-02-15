package com.jinternals.event.bus.common.configuration;

public class Consumer {

    private Boolean enabled;

    private String destination;

    private String Concurrency;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getConcurrency() {
        return Concurrency;
    }

    public void setConcurrency(String concurrency) {
        Concurrency = concurrency;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
