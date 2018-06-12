package cn.ted.process.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ted(lyc)
 * @version : id: ContextSetter , v 0.1 2018/1/2 13:38 Ted(lyc)
 * @Description 声明上下文属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ContextSetter {
    String[] values();
}