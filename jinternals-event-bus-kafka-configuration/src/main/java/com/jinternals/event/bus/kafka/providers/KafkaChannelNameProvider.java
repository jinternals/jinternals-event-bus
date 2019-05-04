package com.jinternals.event.bus.kafka.providers;

import com.jinternals.event.bus.consumer.channel.ChannelNameProvider;

public class KafkaChannelNameProvider implements ChannelNameProvider {

    private String prefix = "kafka.channel";
    private String suffix = "dynamic";

    public String name(Class cls) {
        return String.format("%s.%s.%s", prefix, cls.getName(), suffix);
    }

}
