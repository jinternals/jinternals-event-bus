package com.jinternals.event.bus.activemq.configuration;

import com.jinternals.event.bus.activemq.properties.ActivemqEventBusConsumerProperties;
import com.jinternals.event.bus.activemq.properties.ActivemqEventBusProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ActivemqConsumerConfiguration.class,
        ActivemqBrokerConfiguration.class,
        ActivemqChannelConfiguration.class,
        ActivemqMessageConverterConfiguration.class,
        ActivemqActuatorsConfiguration.class
})
@EnableConfigurationProperties({ActivemqEventBusProperties.class, ActivemqEventBusConsumerProperties.class})
public class ActivemqEventBusConsumerAutoConfiguration {


}
