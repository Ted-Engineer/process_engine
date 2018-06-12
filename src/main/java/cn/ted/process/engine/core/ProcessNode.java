package cn.ted.process.engine.core;

import cn.ted.process.engine.Action;
import cn.ted.process.engine.aspect.Aspect;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Ted(lyc)
 * @version : id: Node , v 0.1 2017/12/29 14:13 Ted(lyc)
 * @Description 流程存储节点
 */
public interface ProcessNode extends Serializable {
    /**
     * 获得唯一键
     * @return
     */
    public String getUnitKey();

    /**
     * 获取前一个节点唯一键
     * @return
     */
    public String getPreNodeKey();

    /**
     * 设置前一个节点唯一键
     * @param preNodeKey
     */
    public void setPreNodeKey(String preNodeKey);

    /**
     * 是否是根节点
     * @return
     */
    public boolean isRoot();

    /**
     * 设置根节点标示
     * @param root
     */
    public void setRoot(boolean root);

    /**
     * 获取节点的动作
     * @return
     */
    public Action getAction();

    /**
     * 设置节点动作
     * @param action
     */
    public void setAction(Action action);

    /**
     * 获得后续节点
     * @return
     */
    public LinkedHashMap<Object, ProcessNode> getNexts();

    /**
     * 设置后续节点
     * @param nexts
     */
    public void setNexts(LinkedHashMap<Object, ProcessNode> nexts);

    /**
     * 获取异常节点
     * @return
     */
    public LinkedHashMap<Class, ProcessNode> getExceptionNexts();

    /**
     * 所有叶子
     * @return
     */
    public Collection<ProcessNode> allLeafs();

    /**
     * 增加环切
     * @return
     */
    public List<Aspect> getAroundAspect();

    /**
     * 增加后切
     * @return
     */
    public List<Aspect> getAfterAspect();

    /**
     * 增加前切
     * @return
     */
    public List<Aspect> getBeforeAspect();

}