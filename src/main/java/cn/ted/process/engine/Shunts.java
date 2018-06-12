package cn.ted.process.engine;

import cn.ted.process.engine.shunt.InjectedShunt;

/**
 * @author Ted(lyc)
 * @version : id: Shunts , v 0.1 2018/1/2 13:31 Ted(lyc)
 */
public final class Shunts {

    /**
     * 创建一个boolean类型做为比较器的分流器
     * @param matcher  true 表示当前action执行成功 false 表示当前action执行失败
     * @return
     */
    public static InjectedShunt injectedBoolShunt(boolean matcher) {
        InjectedShunt shunt = new InjectedShunt<Boolean, Response>(matcher);
        return shunt;
    }

    public static InjectedShunt injectedBoolShunt(boolean matcher, Response obj) {
        InjectedShunt shunt = new InjectedShunt<Boolean, Response>(matcher, obj);
        return shunt;
    }

    /**
     * 创建一个Integer类型做为比较器的分流器
     * @param matcher  true 表示当前action执行成功 false 表示当前action执行失败
     * @return
     */
    public static InjectedShunt injectedIntegerShunt(Integer matcher) {
        InjectedShunt shunt = new InjectedShunt<Integer, Response>(matcher);
        return shunt;
    }

    public static InjectedShunt injectedIntegerShunt(Integer matcher, Response obj) {
        InjectedShunt shunt = new InjectedShunt<Integer, Response>(matcher, obj);
        return shunt;
    }

}