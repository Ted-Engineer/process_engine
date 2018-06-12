package cn.ted.process.engine;

import java.util.Map;

/**
 * @author: Ted(lyc)
 * @Date: 2018/4/17 0017 12:33
 * @Description:
 */
public final class ProcessContextFactory {

    /**
     * 创建默认流程上下文
     * @param initParam 流程初始化需要用的参数
     * @param processName 流程名称
     * @return
     */
    public static final DefaultProcessContext createDefaultProcessCtx(Map<String,Object> initParam,String processName){
        return new DefaultProcessContext(initParam,processName);
    }

    /**
     * 创建默认流程上下文
     * @param processName 流程名称
     * @return
     */
    public static final DefaultProcessContext createDefaultProcessCtx(String processName){
        return new DefaultProcessContext(processName);
    }
}
