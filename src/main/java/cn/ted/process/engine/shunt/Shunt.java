package cn.ted.process.engine.shunt;

import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: shunt , v 0.1 2017/12/28 21:47 Ted(lyc)
 * @Description 分流器接口
 */
public interface Shunt<Matcher extends Comparable<Matcher>, Entity> {
    /**
     * 分流方式的实现
     * @param condition
     * @return
     */
    public int shunt(Object condition);

    /**
     * 获取执行结果比较符
     * @return
     */
    public Matcher getMatcher();

    /**
     * 获取执行结果
     * @return
     */
    public Entity getResult();

    /**
     * 设置执行结果
     * @param entity
     * @return
     */
    public Shunt setResult(Entity entity);

    /**
     * 设置注入参数
     * @param key
     * @param val
     * @return
     */
    public Shunt addInjectedParam(String key, Object val);

    /**
     * 获取注入参数集合
     * @return
     */
    public Map<Object, Object> getInjectedParam();

}