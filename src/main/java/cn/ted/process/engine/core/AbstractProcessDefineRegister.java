package cn.ted.process.engine.core;

import cn.ted.process.engine.Context;
import cn.ted.process.engine.aspect.Aspect;
import cn.ted.process.engine.shunt.Shunt;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: AbstractProcessDefineRegister , v 0.1 2018/1/4 17:41 Ted(lyc)
 * @Description 抽象流程定义注册
 */
@Slf4j
public abstract class AbstractProcessDefineRegister implements ProcessDefineRegister {

    /**
     * 异步执行线程池
     */
    @Resource
    private ThreadPoolTaskExecutor controllerPool;

    /**
     * 获得流程定义器
     * @return
     */
    protected abstract ProcessDefine createProcessDefine();

    @Override
    public final Shunt run(Context context) throws RuntimeException {
        return doProcess(context);
    }

    @Override
    public void aSyncRun(Context context) {
        controllerPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doProcess(context);
                } catch (Exception e) {
                    log.error("[异步执行任务] 失败: ", e, e.getMessage());
                }
            }
        });
    }

    private void logPath(LinkedHashMap<ProcessNode, Object> executePath) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<ProcessNode, Object> entry : executePath.entrySet()) {
            sb.append(entry.getKey().getAction().getClass().getName() + " result : " + entry.getValue() + " , ");
        }
        log.info("[AbstractProcessDefineRegister] execute path : " + sb.toString());
    }

    /**
     * 递归执行
     * @param processNode
     * @param context
     * @param executePath
     * @return
     * @throws IllegalStateException
     */
    private Shunt execute(ProcessNode processNode, Context context, LinkedHashMap<ProcessNode, Object> executePath) throws IllegalStateException {
        Shunt result = null;
        try {
            doBefore(context, processNode);
            result = processNode.getAction().execute(context);
            executePath.put(processNode, result);
            doAfter(context, processNode, result);
            return doNext(processNode, context, result, executePath);
        } catch (RuntimeException e) {
            log.info(e.getMessage(), e);
            executePath.put(processNode, e);
            return doNext(processNode, context, e, executePath);
        }
    }

    private Shunt doNext(ProcessNode processNode, Context context, RuntimeException e, LinkedHashMap<ProcessNode, Object> executePath) {
        ProcessNode always = processNode.getNexts().get(null);
        if (always != null) {
            return execute(always, context, executePath);
        }
        for (Class condition : processNode.getExceptionNexts().keySet()) {
            if (e.getClass().isAssignableFrom(condition)) {
                return execute(processNode.getExceptionNexts().get(condition), context, executePath);
            }
        }
        throw e;
    }

    private Shunt doNext(ProcessNode processNode, Context context, Shunt result, LinkedHashMap<ProcessNode, Object> executePath) {
        ProcessNode always = processNode.getNexts().get(null);
        if (always != null) {
            return execute(always, context, executePath);
        }
        for (Object condition : processNode.getNexts().keySet()) {
            if (result.shunt(condition) == 0) {
                return execute(processNode.getNexts().get(condition), context, executePath);
            }
        }
        log.info("[AbstractProcessDefineRegister] this action : " + processNode.getAction().getClass().getName() + " is the last one ");
        return result;
        //throw new IllegalStateException("[AbstractProcessDefineRegister] according action: " + processNode.getAction().getClass() + " execute result: " + result.getMatcher() + " do not find any next action, please check processDefine.");
    }

    private void doAfter(Context context, ProcessNode node, Shunt result) {
        try {
            if (result.getMatcher().compareTo(new Boolean(false)) != 0) {
                return;
            }
            if (CollectionUtils.isNotEmpty(node.getAfterAspect())) {
                for (Aspect aspect : node.getAfterAspect()) {
                    aspect.execute(context);
                }
            }
        } catch (Exception ex) {
            log.warn("[AbstractProcessDefineRegister] execute node: " + node.getAction().getName() + " after aspect error,{}", ex, ex.getMessage());
        }
    }

    private void doBefore(Context context, ProcessNode node) {
        try {
            if (CollectionUtils.isNotEmpty(node.getBeforeAspect())) {
                for (Aspect aspect : node.getBeforeAspect()) {
                    aspect.execute(context);
                }
            }
        } catch (Exception ex) {
            log.warn("[AbstractProcessDefineRegister] execute node: " + node.getAction().getName() + " before aspect error,{}", ex, ex.getMessage());
        }
    }

    private Shunt doProcess(Context context) {
        ProcessDefine processDefine = createProcessDefine();
        if (processDefine == null) {
            throw new IllegalStateException("ProcessDefine can not be null!");
        }
        if (context == null) {
            throw new IllegalStateException("Context can not be null!");
        }
        ProcessNode root = createProcessDefine().getProcessStore().getRootNode();
        if (root == null) {
            throw new IllegalStateException("DefineStartAction can not be null!");
        }

        LinkedHashMap<ProcessNode, Object> executePath = Maps.newLinkedHashMap();

        Shunt shunt = execute(root, context, executePath);
        logPath(executePath);
        return shunt;
    }
}