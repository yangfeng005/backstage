package com.backstage.system.timecost;

import java.lang.annotation.*;

/**
 * @author yangfeng
 * <p>
 * 方法执行耗时
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TimeCost {
    String name() default "";
}
