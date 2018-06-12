package cn.ted.process.engine;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: Context , v 0.1 2017/12/28 21:51 Ted(lyc)
 * @Description
 */
public interface Context<Matcher extends Comparable<Matcher>> extends Serializable {

    /**
     * 获取用户使用的参数
     * @return
     */
    Map<String, Object> getUserProperties();

    /**
     * 流程定义名称
     * @return
     */
    String getName();

    /**
     * 获取上个action执行结果分流器的matcher
     * @return
     */
    Matcher getMatcher();
}