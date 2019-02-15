package com.jinternals.event.bus.consumer.channel;


import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

public class DynamicPublishSubscribeChannel  extends PublishSubscribeChannel {

    @Override
    @Transactional
    public boolean send(Message<?> message) {
        return super.send(message);
    }


}
