package cn.ted.process.engine.core;


import cn.ted.process.engine.Context;
import cn.ted.process.engine.shunt.Shunt;

/**
 * @author Ted(lyc)
 * @version : id: ProcessInvoker , v 0.1 2017/12/29 10:01 Ted(lyc)
 * @Description  流程执行者
 */
public interface ProcessDefineInvoker {

    /**
     * 运行流程节点逻辑
     * @param context 流程上下文
     * @return 分流器
     */
    public Shunt run(Context context);

    /**
     * 异步执行
     * @param context
     * @return
     */
    public void aSyncRun(Context context);

}