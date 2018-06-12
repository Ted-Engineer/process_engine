package cn.ted.process.engine.core;


import cn.ted.process.engine.Action;
import cn.ted.process.engine.controller.Controller;
import cn.ted.process.engine.handler.Handler;
import cn.ted.process.engine.switcher.Switcher;

/**
 * @author Ted(lyc)
 * @version : id: ProcessDefine , v 0.1 2017/12/28 20:38 Ted(lyc)
 * @Description 流程定义接口
 */
public interface ProcessDefine<Matcher extends Comparable<Matcher>, Entity> extends AspectProcessInvoker {

    /**
     * 定义流程开始动作
     * @param action
     */
    public void defineStart(Action action);

    /**
     * 执行某个动作
     * @param action
     * @return
     */
    public ProcessDefine doHandler(Handler action);

    /**
     * 执行某个选择器
     * @param switcher
     * @return
     */
    public ProcessDefine doSwitcher(Switcher switcher);

    /**
     * 执行控制器
     * @param controller
     * @return
     */
    public ProcessDefine doController(Controller controller);

    /**
     * 当执行某个动作后结果
     * @param condition
     * @return
     */
    public ProcessDefine when(Matcher condition);

    /**
     * 下一个动作执行啥
     * @param action
     * @return
     */
    public ProcessDefine go(Action action);

    /**
     * 执行完某个动作后，无论如何都继续执行某个动作
     * @return
     */
    public ProcessDefine always();

    /**
     * 执行动作后，发生XX异常
     * @param exception
     * @return
     */
    public ProcessDefine exception(Class<RuntimeException> exception);

    /**
     * 获取流程存储器
     * @return
     */
    public ProcessStore getProcessStore();

    /**
     * 默认成功执行
     * @return
     */
    public Entity success();

    /**
     * 默认失败执行
     * @return
     */
    public Entity failure();
}