package cn.ted.process.engine.handler;


import cn.ted.process.engine.Action;

/**
 * @author Ted(lyc)
 * @version : id: Handler , v 0.1 2018/1/8 20:27 Ted(lyc)
 * @Description
 */
public interface Handler<Matcher extends Comparable<Matcher>, Entity> extends Action<Matcher, Entity> {
}