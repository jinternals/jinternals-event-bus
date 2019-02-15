package com.jinternals.event.bus.common.utils;

import com.jinternals.event.bus.common.annotations.EventMapper;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class EventUtils {

    public static Map<String, Class<?>> getTypeMapping(String pkg) {

        Reflections reflections = new Reflections(pkg);

        Set<Class<?>> eventClasses = reflections.getTypesAnnotatedWith(EventMapper.class);

        Map<String, Class<?>> typeMapping = eventClasses
                .stream()
                .collect(toMap(cls -> cls.getAnnotation(EventMapper.class).value(), identity()));

        return typeMapping;
    }

}
