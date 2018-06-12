package cn.ted.process.engine;

import cn.ted.process.engine.shunt.Shunt;

/**
 * @author Ted(lyc)
 * @version : id: Action , v 0.1 2017/12/28 15:52 Ted(lyc)
 * @Description 动作接口
 */
public interface Action<Matcher extends Comparable<Matcher>, Entity> {

    /**
     * 动作执行入口
     * 1、不能抛出Exeption
     * 2、允许抛出RuntimeException
     * @param context
     * @return
     * @throws RuntimeException
     */
    public Shunt<Matcher, Entity> execute(Context context);

    /**
     * 每个Action的名字
     * 禁止为null
     * @return
     */
    public String getName();
}