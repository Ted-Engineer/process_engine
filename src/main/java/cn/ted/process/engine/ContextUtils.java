package cn.ted.process.engine;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ted(lyc)
 * @version : id: ContextAspect , v 0.1 2018/1/2 13:52 Ted(lyc)
 * @Description 上下文工具
 */
public class ContextUtils {

    /**
     * 通过key 获取map中的参数
     * @param key
     * @param context
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getValueByKey(String key, Context context, Class<T> clazz) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return clazz.cast(context.getUserProperties().get(key));
    }
}