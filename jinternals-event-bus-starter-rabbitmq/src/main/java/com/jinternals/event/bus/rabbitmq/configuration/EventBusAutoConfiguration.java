package com.jinternals.event.bus.rabbitmq.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RabbitmqConsumerConfiguration.class,
        RabbitmqBrokerConfiguration.class,
        RabbitmqChannelConfiguration.class,
        RabbitmqProducerConfiguration.class,
        RabbitmqMessageConverterConfiguration.class,
        RabbitmqActuatorsConfiguration.class
})
@EnableConfigurationProperties(RabbitmqEventBusProperties.class)
public class EventBusAutoConfiguration {

}
