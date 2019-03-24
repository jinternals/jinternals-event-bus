package com.jinternals.event.bus.producer.gateway.annotation;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface EventRoutingId {
}
