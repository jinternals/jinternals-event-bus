package com.jinternals.event.bus.activemq.configuration;

import com.jinternals.event.bus.activemq.providers.ActivemqChannelNameProvider;
import com.jinternals.event.bus.consumer.channel.ChannelNameProvider;
import com.jinternals.event.bus.consumer.channel.EventChannelBeanFactoryPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;

@Configuration
@ConditionalOnProperty(prefix = "event.bus.activemq.consumer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActivemqChannelConfiguration {

    @Bean("activemqChannelNameProvider")
    public ChannelNameProvider activemqChannelNameProvider() {
        return new ActivemqChannelNameProvider();
    }

    @Bean
    public EventChannelBeanFactoryPostProcessor eventChannelBeanFactoryPostProcessor(
            @Qualifier("activemqChannelNameProvider") ChannelNameProvider namingStrategy,
            @Value("${event.bus.activemq.listener-package}") String pkg) {
        return new EventChannelBeanFactoryPostProcessor(pkg, namingStrategy);
    }

    @Bean
    public IntegrationFlow activemqIntegrationFlow(
            @Qualifier("activemqMessageProducerSupport") MessageProducerSupport messageProducerSupport,
            ChannelNameProvider channelNameProvider) {

        return IntegrationFlows
                .from(messageProducerSupport)
                .route(message -> channelNameProvider.name(message.getClass()))
                .get();
    }


}
