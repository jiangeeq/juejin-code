package com.ppmoney.wm.trade.commons.log.annotation;


import com.ppmoney.wm.trade.commons.log.strategy.SensitiveCommonStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元注解，定义注解被保留策略，一般有三种策略
 * RetentionPolicy.SOURCE 注解只保留在源文件中，在编译成class文件的时候被遗弃
 * RetentionPolicy.CLASS 注解被保留在class中，但是在jvm加载的时候被抛弃，这个是默认的声明周期
 * RetentionPolicy.RUNTIME 注解在jvm加载的时候仍被保留
 * Target(){ElementType.METHOD} 定义了注解声明在哪些元素之前
 *
 * @author jiangpeng
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysLog {
    // 描述
    String description() default "";

    // 脱敏字段
    String[] params() default {"mobile", "phoneNo", "idCardNo", "userName", "realName", "bankcardNo"};

    // 脱敏规则
    Class<? extends SensitiveStrategy> strategy() default SensitiveCommonStrategy.class;

    // 扩展脱敏字段
    String[] extParams() default "";
}
