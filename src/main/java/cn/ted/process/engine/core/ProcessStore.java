package cn.ted.process.engine.core;


import cn.ted.process.engine.Action;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ted(lyc)
 * @version : id: Store , v 0.1 2017/12/29 14:14 Ted(lyc)
 * @Description 流程存储器
 */
public interface ProcessStore extends Serializable {

    /**
     * 根据动作找到流程节点
     * @param action
     * @param nodes
     */
    public void findNodeByAction(Action action, List<ProcessNode> nodes);

    /**
     * 获取开始执行动作节点
     * @return
     */
    public ProcessNode getRootNode();

    /**
     * 获取开始执行动作节点
     * @param rootNode
     */
    public void setRootNode(ProcessNode rootNode);

    /**
     * 所有node
     * @return
     */
    public List<ProcessNode> allNode();
}