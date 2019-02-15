package com.jinternals.event.bus.rabbitmq.configuration;

import com.jinternals.event.bus.consumer.channel.ChannelNameProvider;
import com.jinternals.event.bus.consumer.channel.EventChannelBeanFactoryPostProcessor;
import com.jinternals.event.bus.rabbitmq.providers.RabbitmqChannelNameProvider;
import com.jinternals.event.bus.common.utils.EventUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;

import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "event.bus.rabbitmq.consumer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RabbitmqChannelConfiguration {

    @Bean("rabbitmqChannelNameProvider")
    public ChannelNameProvider rabbitmqChannelNameProvider() {
        return new RabbitmqChannelNameProvider();
    }

    @Bean("rabbitmqEventChannelBeanFactoryPostProcessor")
    public EventChannelBeanFactoryPostProcessor eventChannelBeanFactoryPostProcessor(
            @Qualifier("rabbitmqChannelNameProvider") ChannelNameProvider namingStrategy,
            @Value("${event.bus.rabbitmq.listener-package}") String pkg) {
        return new EventChannelBeanFactoryPostProcessor(pkg, namingStrategy);
    }

    @Bean
    public IntegrationFlow activemqIntegrationFlow(
            @Qualifier("rabbitmqMessageProducerSupport") MessageProducerSupport messageProducerSupport,
            ChannelNameProvider channelNameProvider) {

        return IntegrationFlows
                .from(messageProducerSupport)
                .route(message -> channelNameProvider.name(message.getClass()))
                .get();
    }


    @Bean("rabbitmqEventMappings")
    public Map<String, Class<?>> getTypeMapping(RabbitmqEventBusProperties rabbitmqEventBusProperties) {

        return EventUtils.getTypeMapping(rabbitmqEventBusProperties.getEventPackage());

    }


}
