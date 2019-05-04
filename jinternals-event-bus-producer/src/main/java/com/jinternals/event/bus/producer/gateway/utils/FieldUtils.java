package com.jinternals.event.bus.producer.gateway.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ReflectionUtils.doWithFields;

public class FieldUtils {

    public static List<Field> getAnnotatedFields(Class type, Class annotation) {
        List<Field> fields = new ArrayList<>();

        doWithFields(type, field -> {
            if (field.isAnnotationPresent(annotation)) {
                field.setAccessible(true);
                fields.add(field);
            }
        });

        return fields;
    }

}
