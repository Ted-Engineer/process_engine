package cn.ted.process.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ted(lyc)
 * @version : id: ContextGetter , v 0.1 2018/1/2 13:39 Ted(lyc)
 * @Description 上下文声明式注解
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextGetter {
    Element[] values() default {};
}