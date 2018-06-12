package cn.ted.process.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ted(lyc)
 * @version : id: Element , v 0.1 2018/1/4 19:00 Ted(lyc)
 * @Description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Element {

    String key();

    Class type();
}