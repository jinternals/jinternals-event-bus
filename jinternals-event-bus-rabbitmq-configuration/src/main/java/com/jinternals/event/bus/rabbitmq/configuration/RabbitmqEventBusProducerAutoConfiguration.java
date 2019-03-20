package com.jinternals.event.bus.rabbitmq.configuration;

import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProducerProperties;
import com.jinternals.event.bus.rabbitmq.properties.RabbitmqEventBusProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RabbitmqBrokerConfiguration.class,
        RabbitmqProducerConfiguration.class,
        RabbitmqMessageConverterConfiguration.class,
})
@EnableConfigurationProperties({RabbitmqEventBusProperties.class, RabbitmqEventBusProducerProperties.class})
public class RabbitmqEventBusProducerAutoConfiguration {


}
