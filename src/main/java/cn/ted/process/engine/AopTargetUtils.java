package cn.ted.process.engine;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * @author Ted(lyc)
 * @version : id: ContextAspect , v 0.1 2018/1/2 13:52 Ted(lyc)
 */
public class AopTargetUtils {
    public static Object getTarget(Object proxy) {

        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib  
            return getCglibProxyTargetObject(proxy);
        }

    }
    private static Object getCglibProxyTargetObject(Object proxy) {
        try {
            Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
            h.setAccessible(true);
            Object dynamicAdvisedInterceptor = h.get(proxy);

            Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
            advised.setAccessible(true);

            return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static Object getJdkDynamicProxyTargetObject(Object proxy) {
        try {
            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            AopProxy aopProxy = (AopProxy) h.get(proxy);

            Field advised = aopProxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);

            return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
