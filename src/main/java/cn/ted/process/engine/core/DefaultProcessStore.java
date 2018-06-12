package cn.ted.process.engine.core;

import cn.ted.process.engine.Action;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Ted(lyc)
 * @version : id: ProcessStore , v 0.1 2017/12/28 21:42 Ted(lyc)
 * @Description
 */
public class DefaultProcessStore implements ProcessStore {

    private static final long serialVersionUID = 1645327383802762616L;

    /**
     *  流程开始节点
     */
    private ProcessNode       rootNode;

    @Override
    public void findNodeByAction(Action action, List<ProcessNode> nodes) {
        this.search(this.rootNode, action, nodes);
    }

    /**
     * 递归查找
     * @param node
     * @param action
     * @return
     */
    private void search(ProcessNode node, Action action, List<ProcessNode> nodes) {
        if (node == null) {
            return;
        }
        if (node.getAction().getClass().getName().equals(action.getClass().getName())) {
            nodes.add(node);
        }
        for (ProcessNode processNode : node.allLeafs()) {
            this.search(processNode, action, nodes);
        }
        return;
    }

    /**
     * 递归遍历
     * @param node
     * @param nodes
     * @return
     */
    private void search(ProcessNode node, List<ProcessNode> nodes) {
        if (node == null) {
            return;
        }
        if (node.getAction() != null) {
            nodes.add(node);
        }
        for (ProcessNode processNode : node.allLeafs()) {
            this.search(processNode, nodes);
        }
        return;
    }

    @Override
    public List<ProcessNode> allNode() {
        List<ProcessNode> nodes = Lists.newArrayList();
        this.search(this.rootNode, nodes);
        return nodes;
    }

    @Override
    public ProcessNode getRootNode() {
        return rootNode;
    }

    @Override
    public void setRootNode(ProcessNode rootNode) {
        this.rootNode = rootNode;
    }
}