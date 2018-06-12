package cn.ted.process.engine.core;


import cn.ted.process.engine.Action;
import cn.ted.process.engine.aspect.Aspect;

/**
 * @author Ted(lyc)
 * @version : id: AspectProcessInvoker , v 0.1 2017/12/29 10:01 Ted(lyc)
 * @Description  切面执行者
 */
public interface AspectProcessInvoker {
    /**
     * 给所有handler增加前切
     * @param aspect
     * @return
     */
    ProcessDefine handlerBefore(Aspect aspect);

    /**
     * 给某个ACTION 增加前切
     * @param action
     * @param aspect
     * @return
     */
    ProcessDefine addBefore(Action action, Aspect aspect);

    /**
     * 移除某个action的某个前切
     * @param action
     * @param aspect
     * @return
     */
    ProcessDefine removeBefore(Action action, Aspect aspect);

    /**
     * 给所有handler增加后切
     * @param aspect
     * @return
     */
    ProcessDefine handlerAfter(Aspect aspect);

    /**
     * 给某个action 增加后切 抛异常不切
     * @param action
     * @param aspect
     * @return
     */
    ProcessDefine addAfter(Action action, Aspect aspect);

    /**
     * 给某个action 减少后切
     * @param action
     * @param aspect
     * @return
     */
    ProcessDefine removeAfter(Action action, Aspect aspect);

    /**
     * 对多个action 移除后切
     * @param aspect
     * @param actions
     * @return
     */
    ProcessDefine removeAfter(Aspect aspect, Action... actions);


}