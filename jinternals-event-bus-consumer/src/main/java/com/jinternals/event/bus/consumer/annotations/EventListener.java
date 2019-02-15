package com.jinternals.event.bus.consumer.annotations;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {
}
