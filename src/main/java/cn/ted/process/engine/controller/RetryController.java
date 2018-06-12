package cn.ted.process.engine.controller;


import cn.ted.process.engine.Context;
import cn.ted.process.engine.Shunts;
import cn.ted.process.engine.handler.Handler;
import cn.ted.process.engine.shunt.Shunt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: Ted
 * @Date: 2018/5/2 0002 15:51
 * @Description:
 */
@Slf4j
public class RetryController<Matcher extends Comparable<Matcher>, Entity> implements Controller<Matcher, Entity> {

    private static final Logger      LOGGER    = LoggerFactory.getLogger(RetryController.class);

    private Handler<Matcher, Entity> handler;

    private Matcher                  target;

    private long                     timeSpace;

    private long                     times;

    public RetryController(Handler<Matcher, Entity> handler, Matcher target, long timeSpace, long times) {
        this.handler = handler;
        this.target = target;
        this.timeSpace = timeSpace;
        this.times = times;
    }

    @Override
    public Shunt<Matcher, Entity> execute(Context context) {
        for (int i = 1; i <= times; i++) {
            Shunt<Matcher, Entity> shunt = handler.execute(context);

            if (target.compareTo(shunt.getMatcher()) == NumberUtils.INTEGER_ZERO) {
                return shunt;
            } else {
                try {
                    Thread.sleep(timeSpace * 1000);
                } catch (InterruptedException e) {
                    log.error("[RetryController try try : ]" + handler.getName() + "has error:{}", e, e.getMessage());
                }
            }
        }
        return Shunts.injectedBoolShunt(Controller.FAILURE_ENDING);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    public void setHandler(Handler<Matcher, Entity> handler) {
        this.handler = handler;
    }

    public void setTimeSpace(long timeSpace) {
        this.timeSpace = timeSpace;
    }

    public void setTimes(long times) {
        this.times = times;
    }
}
