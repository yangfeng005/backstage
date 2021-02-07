package com.backstage.system.log;

import java.lang.annotation.*;

/**
 * @author yangfeng
 * @date 2019年9月10日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogOperation {
    String action() default "";
}
