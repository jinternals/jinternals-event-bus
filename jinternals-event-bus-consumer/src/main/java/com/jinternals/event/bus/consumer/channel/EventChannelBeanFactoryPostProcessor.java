package com.jinternals.event.bus.consumer.channel;


import com.jinternals.event.bus.consumer.annotations.EventListener;
import org.reflections.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.groupingBy;

public class EventChannelBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private String packagesToScan;

    private ChannelNameProvider channelNameProvider;

    public EventChannelBeanFactoryPostProcessor(String packagesToScan, ChannelNameProvider channelNameProvider){
        this.packagesToScan = packagesToScan;
        this.channelNameProvider = channelNameProvider;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);


        Map<? extends Class<?>, List<Method>> classMethodList = scanner.findCandidateComponents(packagesToScan)
                .stream()
                .flatMap(o -> stream(ReflectionUtils.forName(o.getBeanClassName()).getMethods()))
                .filter(method -> method.isAnnotationPresent(EventListener.class))
                .collect(groupingBy(o -> o.getParameterTypes()[0]));


        classMethodList
                .forEach((beanType, methods) -> registerChannel(beanType,methods,beanFactory));


    }


    private void registerChannel(Class<?> eventType,List<Method> methods , ConfigurableListableBeanFactory factory){
        GenericBeanDefinition definition = new GenericBeanDefinition();

        definition.setBeanClass(DynamicChannelFactoryBean.class);

        final ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addGenericArgumentValue(channelNameProvider.name(eventType));
        argumentValues.addGenericArgumentValue(methods);

        definition.setConstructorArgumentValues(argumentValues);

        ((DefaultListableBeanFactory) factory).registerBeanDefinition(channelNameProvider.name(eventType),definition);

    }

}
