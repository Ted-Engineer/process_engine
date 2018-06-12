package cn.ted.process.engine.core;

import cn.ted.process.engine.Action;
import cn.ted.process.engine.aspect.Aspect;
import cn.ted.process.engine.controller.Controller;
import cn.ted.process.engine.handler.Handler;
import cn.ted.process.engine.switcher.Switcher;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author Ted(lyc)
 * @version : id: DefaultProcessDefine , v 0.1 2017/12/28 21:30 Ted(lyc)
 * @Description 默认流程定义器
 */

public final class DefaultProcessDefine<Matcher extends Comparable<Matcher>, Entity> implements ProcessDefine<Matcher, Entity> {

    /** 流程存贮器 */
    protected ProcessStore      processStore     = new DefaultProcessStore();

    /** 临时变量，do */
    protected List<ProcessNode> lastProcessNodes = Lists.newArrayList();

    /** 临时变量，最后一个条件 */
    protected Object            lastCondition;

    @Override
    public void defineStart(Action action) {
        ProcessNode processNode = new DefaultProcessNode();
        processNode.setRoot(true);
        processNode.setAction(action);
        processStore.setRootNode(processNode);
    }

    @Override
    public ProcessDefine doHandler(Handler action) {
        this.lastProcessNodes.clear();
        this.processStore.findNodeByAction(action, this.lastProcessNodes);
        return this;
    }

    @Override
    public ProcessDefine doSwitcher(Switcher switcher) {
        this.lastProcessNodes.clear();
        this.processStore.findNodeByAction(switcher, this.lastProcessNodes);
        return this;
    }

    @Override
    public ProcessDefine when(Matcher condition) {
        this.lastCondition = condition;
        return this;
    }

    @Override
    public ProcessDefine always() {
        this.lastCondition = null;
        lastProcessNodes.stream().forEach(x -> x.getNexts().clear());
        return this;
    }

    @Override
    public ProcessDefine exception(Class<RuntimeException> exception) {
        this.lastCondition = exception;
        return this;
    }

    @Override
    public ProcessDefine go(Action action) {
        this.lastProcessNodes.stream().forEach(x -> {
            ProcessNode processNode = new DefaultProcessNode();
            processNode.setAction(action);
            processNode.setPreNodeKey(x.getUnitKey());
            if (!(lastCondition instanceof Class)) {
                x.getNexts().put(lastCondition, processNode);
            } else {
                x.getExceptionNexts().put((Class) lastCondition, processNode);
            }
        });
        return this;
    }

    @Override
    public ProcessDefine doController(Controller controller) {
        this.lastProcessNodes.clear();
        this.processStore.findNodeByAction(controller, this.lastProcessNodes);
        return this;
    }

    @Override
    public ProcessStore getProcessStore() {
        return processStore;
    }

    @Override
    public ProcessDefine handlerBefore(Aspect aspect) {
        List<ProcessNode> nodes = processStore.allNode();
        nodes.stream().forEach(node -> node.getBeforeAspect().add(aspect));
        return this;
    }

    @Override
    public ProcessDefine addBefore(Action action, Aspect aspect) {
        List<ProcessNode> nodes = Lists.newArrayList();
        processStore.findNodeByAction(action, nodes);
        if (CollectionUtils.isNotEmpty(nodes)) {
            nodes.stream().forEach(node -> node.getBeforeAspect().add(aspect));
        }
        return this;
    }

    @Override
    public ProcessDefine removeBefore(Action action, Aspect aspect) {
        List<ProcessNode> nodes = Lists.newArrayList();
        processStore.findNodeByAction(action, nodes);
        if (CollectionUtils.isNotEmpty(nodes)) {
            nodes.stream().forEach(node -> node.getBeforeAspect().remove(aspect));
        }
        return this;
    }

    @Override
    public ProcessDefine handlerAfter(Aspect aspect) {
        List<ProcessNode> nodes = processStore.allNode();
        nodes.stream().forEach(node -> node.getAfterAspect().add(aspect));
        return this;
    }

    @Override
    public ProcessDefine addAfter(Action action, Aspect aspect) {
        List<ProcessNode> nodes = Lists.newArrayList();
        processStore.findNodeByAction(action, nodes);
        if (CollectionUtils.isNotEmpty(nodes)) {
            nodes.stream().forEach(node -> node.getAfterAspect().add(aspect));
        }
        return this;
    }

    @Override
    public ProcessDefine removeAfter(Action action, Aspect aspect) {
        List<ProcessNode> nodes = Lists.newArrayList();
        processStore.findNodeByAction(action, nodes);
        if (CollectionUtils.isNotEmpty(nodes)) {
            nodes.stream().forEach(node -> node.getAfterAspect().remove(aspect));
        }
        return this;
    }

    @Override
    public ProcessDefine removeAfter(Aspect aspect, Action... actions) {
        for (Action action : actions) {
            if (action == null) {
                continue;
            }
            List<ProcessNode> nodes = Lists.newArrayList();
            processStore.findNodeByAction(action, nodes);
            if (CollectionUtils.isNotEmpty(nodes)) {
                nodes.stream().forEach(node -> node.getAfterAspect().remove(aspect));
            }
        }
        return this;
    }

    @Override
    public Entity success() {
        return null;
    }

    @Override
    public Entity failure() {
        return null;
    }
}