package cn.ted.process.engine.core;

import cn.ted.process.engine.Action;
import cn.ted.process.engine.aspect.Aspect;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Ted(lyc)
 * @version : id: ProcessNode , v 0.1 2017/12/28 21:42 Ted(lyc)
 * @Description
 */
public class DefaultProcessNode implements ProcessNode {

    private static final long                  serialVersionUID = 8387026152996928350L;

    private boolean                            root             = false;

    private String                             unitKey          = UUID.randomUUID().toString();

    private String                             preNodeKey;

    private Action action;

    private LinkedHashMap<Object, ProcessNode> nexts            = Maps.newLinkedHashMap();

    private LinkedHashMap<Class, ProcessNode>  exceptionNexts   = Maps.newLinkedHashMap();

    private List<ProcessNode>                  allNodes         = Lists.newArrayList();

    private List<Aspect>                       beforeAspect     = Lists.newArrayList();

    private List<Aspect>                       afterAspect      = Lists.newArrayList();

    private List<Aspect>                       aroundAspect     = Lists.newArrayList();

    @Override
    public String getPreNodeKey() {
        return preNodeKey;
    }

    @Override
    public void setPreNodeKey(String preNodeKey) {
        this.preNodeKey = preNodeKey;
    }

    @Override
    public String getUnitKey() {
        return unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    @Override
    public void setRoot(boolean root) {
        this.root = root;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public LinkedHashMap<Object, ProcessNode> getNexts() {
        return nexts;
    }

    @Override
    public void setNexts(LinkedHashMap<Object, ProcessNode> nexts) {
        this.nexts = nexts;
    }

    @Override
    public LinkedHashMap<Class, ProcessNode> getExceptionNexts() {
        return this.exceptionNexts;
    }

    @Override
    public List<Aspect> getBeforeAspect() {
        return beforeAspect;
    }

    public void setBeforeAspect(List<Aspect> beforeAspect) {
        this.beforeAspect = beforeAspect;
    }

    @Override
    public List<Aspect> getAfterAspect() {
        return afterAspect;
    }

    public void setAfterAspect(List<Aspect> afterAspect) {
        this.afterAspect = afterAspect;
    }

    @Override
    public List<Aspect> getAroundAspect() {
        return aroundAspect;
    }

    public void setAroundAspect(List<Aspect> aroundAspect) {
        this.aroundAspect = aroundAspect;
    }

    @Override
    public Collection<ProcessNode> allLeafs() {
        this.allNodes.clear();
        this.allNodes.addAll(this.nexts.values());
        this.allNodes.addAll(this.exceptionNexts.values());
        return this.allNodes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultProcessNode{");
        sb.append("unitKey='").append(unitKey).append('\'');
        sb.append(", preNodeKey='").append(preNodeKey).append('\'');
        sb.append(", action=").append(action);
        sb.append('}');
        return sb.toString();
    }
}