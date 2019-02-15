package com.jinternals.event.bus.consumer.channel;


import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.annotation.ServiceActivatorAnnotationPostProcessor;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;

import java.lang.reflect.Method;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DynamicChannelFactoryBean extends AbstractFactoryBean<PublishSubscribeChannel> {

    private String channelName;

    private List<Method> methods;

    public DynamicChannelFactoryBean(String channelName, List<Method> methods){
        this.channelName = channelName;
        this.methods = methods;
    }

    @Override
    public Class<?> getObjectType() {
        return PublishSubscribeChannel.class;
    }

    @Override
    protected PublishSubscribeChannel createInstance() {
        ConfigurableListableBeanFactory factory =   (ConfigurableListableBeanFactory) getBeanFactory();

        List<ChannelInterceptor> interceptors = newArrayList(factory.getBeansOfType(ChannelInterceptor.class).values());

        PublishSubscribeChannel dynamicChannel = new DynamicPublishSubscribeChannel();

        dynamicChannel.setComponentName(channelName);

        methods.forEach(method -> {
            dynamicChannel.subscribe(getMessageHandler(method,factory));
        });

        dynamicChannel.setInterceptors(interceptors);

        return dynamicChannel;
    }

    private MessageHandler getMessageHandler(Method method, ConfigurableListableBeanFactory beanFactory){
        final Object bean = beanFactory.getBean(method.getDeclaringClass());

        return (MessageHandler) new ServiceActivatorAnnotationPostProcessor(beanFactory)
                .postProcess(bean,bean.getClass().getName(),method, newArrayList());

    }
}
