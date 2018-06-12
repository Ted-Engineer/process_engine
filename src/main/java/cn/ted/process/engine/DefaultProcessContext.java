package cn.ted.process.engine;

import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: DefaultProcessContext , v 0.1 2018/1/4 17:29 Ted(lyc)
 * @Description 默认流程上下文实现
 */
public class DefaultProcessContext extends AbstractProcessContext {
    protected DefaultProcessContext(Map<String, Object> initParam, String name) {
        super(initParam, name);
    }

    protected DefaultProcessContext(String name) {
        super(name);
    }
    //NO-OP
}