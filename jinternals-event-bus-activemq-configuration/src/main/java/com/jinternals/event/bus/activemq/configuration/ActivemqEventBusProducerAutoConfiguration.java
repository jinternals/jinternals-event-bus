package com.jinternals.event.bus.activemq.configuration;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProducerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;

@Configuration
@Import({
        ActivemqBrokerConfiguration.class,
        ActivemqProducerConfiguration.class,
        ActivemqMessageConverterConfiguration.class,
})
@EnableConfigurationProperties({ActivemqEventBusProperties.class, ActivemqEventBusProducerProperties.class})
public class ActivemqEventBusProducerAutoConfiguration {


}
