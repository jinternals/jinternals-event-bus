package com.jinternals.event.bus.activemq.providers;

import com.jinternals.event.bus.consumer.channel.ChannelNameProvider;

public class ActivemqChannelNameProvider implements ChannelNameProvider {

    private String prefix = "activemq.channel";
    private String suffix = "dynamic";

    public String name(Class cls) {
        return String.format("%s.%s.%s", prefix, cls.getName(), suffix);
    }

}
