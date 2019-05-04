package com.jinternals.event.bus.kafka.configuration.producer;

import com.jinternals.event.bus.kafka.configuration.KafkaMessageConverterConfiguration;
import com.jinternals.event.bus.kafka.properties.KafkaEventBusProducerProperties;
import com.jinternals.event.bus.kafka.properties.KafkaEventBusProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({KafkaProducerConfiguration.class, KafkaMessageConverterConfiguration.class})
@EnableConfigurationProperties({KafkaEventBusProperties.class, KafkaEventBusProducerProperties.class})
public class KafkaEventBusProducerAutoConfiguration {

}
