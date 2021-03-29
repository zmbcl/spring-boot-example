package com.neo.ann;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 9:42 上午 2021/3/5
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流注解
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    // 默认每秒放入桶中的token
    double limitNum() default 20;

    String name() default "";
}

