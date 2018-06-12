package cn.ted.process.engine.switcher;


import cn.ted.process.engine.Action;

/**
 * @author Ted(lyc)
 * @version : id: Switcher , v 0.1 2017/12/28 20:52 Ted(lyc)
 * @Description  switcher case 选择器
 */
public interface Switcher<Matcher extends Comparable<Matcher>, Entity> extends Action<Matcher, Entity> {
}