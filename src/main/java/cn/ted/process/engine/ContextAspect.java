package cn.ted.process.engine;


import cn.ted.process.engine.annotation.ContextGetter;
import cn.ted.process.engine.annotation.ContextSetter;
import cn.ted.process.engine.annotation.Element;
import cn.ted.process.engine.shunt.Shunt;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: ContextAspect , v 0.1 2018/1/2 13:52 Ted(lyc)
 * @Description
 */
@Aspect
@Slf4j
public class ContextAspect {


    @Pointcut("@annotation(cn.ted.process.engine.annotation.ContextGetter)")
    public void getAspect() {
    }

    @Pointcut("@annotation(cn.ted.process.engine.annotation.ContextSetter)")
    public void setAspect() {
    }

    @Before(value = "getAspect() && @annotation(contextGetter)")
    public void beforeGet(JoinPoint pjp, ContextGetter contextGetter) throws Throwable {
        // 将Context里的参数，设置到request中
        try {
            Object[] obj = pjp.getArgs();
            AbstractProcessContext context = (AbstractProcessContext) obj[0];
            Element[] values = contextGetter.values();
            Map<String, Object> properties = context.getProperties();
            synchronized(context){
                context.getUserProperties();
            }
            Map<String, Object> userProperties = context.getUserProperties();

            for (Element element : values) {
                if (properties.containsKey(element.key())) {
                    Object value = properties.get(element.key());
                    if (value == null) {
                        userProperties.put(element.key(), value);
                    } else {
                        if (element.type().isAssignableFrom(value.getClass())) {
                            userProperties.put(element.key(), value);
                        } else {
                            throw new IllegalArgumentException("property " + element.key() + " type is " + value.getClass() + "  not equals of " + element.type());
                        }
                    }
                }
            }
        } catch (Throwable e) {
            log.error("ContextGetter error，detail:{}", e, e.getMessage());
        }
    }

    /**
     * 在这里定义前置切面
     * @param pjp
     * @param rvt
     * @param contextSetter
     * @throws Throwable
     */
    @AfterReturning(returning = "rvt", pointcut = "setAspect() && @annotation(contextSetter)")
    public void afterReturn(JoinPoint pjp, Shunt rvt, ContextSetter contextSetter) throws Throwable {
        // 返回的shunt，中的参数设置到Context中
        try {
            Object[] obj = pjp.getArgs();
            AbstractProcessContext context = (AbstractProcessContext) obj[0];
            context.setCvt(rvt);
            String[] values = contextSetter.values();
            Map<String, Object> properties = context.getProperties();
            Map<String, Object> resultProperties = rvt.getInjectedParam();
            for (String key : values) {
                properties.put(key, resultProperties.get(key));
            }
        } catch (Throwable e) {
            log.error("ContextSetter error，detail:{}", e, e.getMessage());
        }
    }
}