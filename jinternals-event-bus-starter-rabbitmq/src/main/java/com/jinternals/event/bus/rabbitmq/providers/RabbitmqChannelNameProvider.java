package com.jinternals.event.bus.rabbitmq.providers;

import com.jinternals.event.bus.consumer.channel.ChannelNameProvider;

public class RabbitmqChannelNameProvider implements ChannelNameProvider {

    private String prefix = "rabbitmq.channel";
    private String suffix = "dynamic";

    public String name(Class cls) {
        return String.format("%s.%s.%s", prefix, cls.getName(), suffix);
    }

}
