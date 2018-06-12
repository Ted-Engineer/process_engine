package cn.ted.process.engine.controller;


import cn.ted.process.engine.Action;

/**
 * @author Ted(lyc)
 * @version : id: Controller , v 0.1 2017/12/28 20:44 Ted(lyc)
 * @Description
 */
public interface Controller<Matcher extends Comparable<Matcher>, Entity> extends Action<Matcher, Entity> {

    public static final Boolean SUCCESS_ENDING = new Boolean(true);

    public static final Boolean FAILURE_ENDING = new Boolean(false);
}