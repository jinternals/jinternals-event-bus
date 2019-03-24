package com.jinternals.event.bus.producer.gateway.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldUtils {

    public static List<Field> getAnnotatedFields(Class type, Class annotation) {
        List<Field> fields = new ArrayList<>();

        ReflectionUtils.doWithFields(type, field -> {
            if (field.isAnnotationPresent(annotation)) {
                field.setAccessible(true);
                fields.add(field);
            }
        });

        return fields;
    }

}
