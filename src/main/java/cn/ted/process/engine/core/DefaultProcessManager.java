package cn.ted.process.engine.core;

import cn.ted.process.engine.AopTargetUtils;
import cn.ted.process.engine.Context;
import cn.ted.process.engine.annotation.ProcessScene;
import cn.ted.process.engine.shunt.Shunt;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: ProcessManagerImpl , v 0.1 2018/1/3 18:11 Ted(lyc)
 * @Description  流程管理器
 */
@Slf4j
public class DefaultProcessManager implements ProcessDefineRegister, ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 异步执行线程池
     */
    @Resource
    private ThreadPoolTaskExecutor controllerPool;

    /** 所有的流程 */
    private final Map<String, AbstractProcessDefineRegister> processDefineMap = Maps.newConcurrentMap();

    @Override
    public void register() {
        Map<String, AbstractProcessDefineRegister> process = applicationContext.getBeansOfType(AbstractProcessDefineRegister.class, false, true);
        if (MapUtils.isEmpty(process)) {
            throw new IllegalStateException("Cannot register ProcessDefine, please make sure the ProcessDefine setup as spring bean");
        }
        for (AbstractProcessDefineRegister processDefineRegister : process.values()) {
            AbstractProcessDefineRegister processDefineRegisterProxy = (AbstractProcessDefineRegister) AopTargetUtils.getTarget(processDefineRegister);
            ProcessScene processName = processDefineRegisterProxy.getClass().getDeclaredAnnotation(ProcessScene.class);
            if (processName == null) {
                throw new IllegalStateException("Cannot register ProcessDefine, please make sure place annotation @ProcessScene on processDefine, processDefine: "
                                                + processDefineRegisterProxy.getClass().getName());
            }
            String processScene = processName.name();
            AbstractProcessDefineRegister pd = this.processDefineMap.get(processScene);
            if (pd != null) {
                throw new IllegalStateException(
                    "Cannot register processDefine, the processDefine name has been register by other processDefine Map, processDefine: " + pd.getClass().getName());
            }
            try {
                processDefineRegisterProxy.register();
            } catch (Exception e) {
                throw new IllegalStateException(
                    "Cannot init processDefine, the processDefine has exception when init, processDefine: " + processDefineRegisterProxy.getClass().getName());
            }

            this.processDefineMap.put(processScene, processDefineRegisterProxy);
            log.info("Register ProcessDefine: {} - {}", processScene, processDefineRegisterProxy.getClass().getName());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /** 注册所有的流程图 */
        register();
    }

    @Override
    public Shunt run(Context context) {
        AbstractProcessDefineRegister process = this.processDefineMap.get(context.getName());
        if (process == null) {
            throw new IllegalArgumentException("the ProcessDefineRegister can not be found by name : " + context.getName());
        }
        try {
            Shunt shunt = process.run(context);
            return shunt;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
    }

    @Override
    public void aSyncRun(Context context) {
        controllerPool.submit(() -> run(context));
    }
}