package com.jinternals.event.bus.rabbitmq.configuration;

import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusConsumerProperties;
import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RabbitmqConsumerConfiguration.class,
        RabbitmqBrokerConfiguration.class,
        RabbitmqChannelConfiguration.class,
        RabbitmqMessageConverterConfiguration.class,
        RabbitmqActuatorsConfiguration.class
})
@EnableConfigurationProperties({RabbitmqEventBusProperties.class, RabbitmqEventBusConsumerProperties.class})
public class RabbitmqEventBusConsumerAutoConfiguration {


}
