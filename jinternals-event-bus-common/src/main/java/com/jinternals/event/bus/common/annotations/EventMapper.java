package com.jinternals.event.bus.common.annotations;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventMapper {

    String value() default "";

}
