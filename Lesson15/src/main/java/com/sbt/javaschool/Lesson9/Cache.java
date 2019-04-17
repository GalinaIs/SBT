package com.sbt.javaschool.Lesson9;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    CacheType cacheType() default CacheType.IN_MEMORY;

    Class[] identityBy() default {};

    int listSize() default 10;

    String cacheNamePrefix() default "";

    boolean zip() default false;
}