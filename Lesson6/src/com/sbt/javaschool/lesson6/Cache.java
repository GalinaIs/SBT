package com.sbt.javaschool.lesson6;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Cache {
    String cacheName() default "cacheObject";
}
