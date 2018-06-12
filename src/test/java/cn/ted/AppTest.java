package cn.ted;

import cn.ted.process.engine.Context;
import cn.ted.process.engine.ProcessContextFactory;
import cn.ted.process.engine.annotation.ProcessScene;
import cn.ted.process.engine.controller.RetryController;
import cn.ted.process.engine.core.AbstractProcessDefineRegister;
import cn.ted.process.engine.core.DefaultProcessDefine;
import cn.ted.process.engine.core.DefaultProcessManager;
import cn.ted.process.engine.core.ProcessDefine;
import cn.ted.process.engine.handler.Handler;
import cn.ted.process.engine.switcher.Switcher;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Resource
    private static Handler<Boolean,Object> oneHandler;
    @Resource
    private static Handler<Boolean,Object>  twoHandler;
    @Resource
    private static Handler<Boolean,Object>  threeHandler;
    @Resource
    private static Handler<Boolean,Object>  fourHandler;
    @Resource
    private static Handler<Boolean,Object>  fiveHandler;
    @Resource
    private static Handler<Boolean,Object>  sixHandler;
    @Resource
    private static Switcher<Integer,Object> oneSwitcher;
    @Resource
    private static DefaultProcessManager defaultProcessManager;


    public static void main(String[] args) {
        Map<String,Object> initParam = Maps.newHashMap();
        Context processCtx = ProcessContextFactory.createDefaultProcessCtx(initParam, "testProcess");
        defaultProcessManager.run(processCtx);
    }

    @ProcessScene(name = "testProcess")
    @Service
    public static class XProcess extends AbstractProcessDefineRegister {
        private ProcessDefine process;
        @Override
        protected ProcessDefine createProcessDefine() {
            return this.process;
        }

        @Override
        public void register() {
            process = new DefaultProcessDefine();
            //定义业务流程第一步
            process.defineStart(oneHandler);
            //定义第一步执行结果为true时，执行 twoHandler
            process.doHandler(oneHandler).when(true).go(twoHandler);
            //定义第一步执行结果为false时，执行 threeHandler
            process.doHandler(oneHandler).when(false).go(threeHandler);
            //twoHandler执行结果为true时，执行 oneSwitcher 选择器
            process.doHandler(twoHandler).when(false).go(oneSwitcher);
            //oneSwitcher 选择器执行结果为 0时，执行 fourHandler
            process.doSwitcher(oneSwitcher).when(NumberUtils.INTEGER_ZERO).go(fourHandler);
            //oneSwitcher 选择器执行结果为 0时，执行 fiveHandler
            process.doSwitcher(oneSwitcher).when(NumberUtils.INTEGER_ONE).go(fiveHandler);
            //重试增强控制器，当执行sixHandler执行结果不为true时，发生重试，每次间隔10秒，重试3次
            process.doController(new RetryController(sixHandler,true,10,3));
        }
    }
}
