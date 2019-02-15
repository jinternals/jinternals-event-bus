package com.jinternals.event.bus.activemq.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;

@Configuration
@Import({
        ActivemqConsumerConfiguration.class,
        ActivemqBrokerConfiguration.class,
        ActivemqChannelConfiguration.class,
        ActivemqProducerConfiguration.class,
        ActivemqMessageConverterConfiguration.class,
        ActivemqActuatorsConfiguration.class
})
@EnableConfigurationProperties({ActivemqEventBusProperties.class})
public class ActivemqEventBusAutoConfiguration {


}
