package com.jinternals.event.bus.common.configuration;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class Consumer {

    private Boolean enabled;

    private String destination;

    private String concurrency;

    @NestedConfigurationProperty
    private Retry retry;

    public Retry getRetry() {
        return retry;
    }

    public void setRetry(Retry retry) {
        this.retry = retry;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(String concurrency) {
        this.concurrency = concurrency;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
