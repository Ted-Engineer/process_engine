package cn.ted.process.engine.aspect;


import cn.ted.process.engine.handler.Handler;

/**
 * @author Ted(lyc)
 * @version : id: Aspect , v 0.1 2018/2/26 10:45 Ted(lyc)
 * @Description
 */
public interface Aspect<Matcher extends Comparable<Matcher>, Entity> extends Handler<Matcher, Entity> {
}