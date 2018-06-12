package cn.ted.process.engine.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ted(lyc)
 * @version : id: Process , v 0.1 2017/12/29 17:12 Ted(lyc)
 * @Description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Service
public @interface ProcessScene {
    String name() default "";
}