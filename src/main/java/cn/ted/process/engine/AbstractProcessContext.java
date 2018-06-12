package cn.ted.process.engine;

import cn.ted.process.engine.shunt.Shunt;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: InjectedProcessRequest , v 0.1 2017/12/29 17:20 Ted(lyc)
 * @Description 抽象上下文
 */
public abstract class AbstractProcessContext implements Context {

    private static final long               serialVersionUID = 3014218639568325828L;
    /**
     * 全局上下文参数集合,用户不可见
     */
    protected Map<String, Object> properties       = Maps.newHashMap();
    /**
     * 全局上下文参数集合,用户可见
     */
    protected Map<String, Object> userProperties   = Maps.newHashMap();
    /**
     * 流程图名称
     */
    private String                          name;
    /**
     * 上一个action执行结果分流器
     */
    private Shunt                           cvt;

    protected AbstractProcessContext(Map<String, Object> initParam, String name) {
        if (initParam != null && initParam.size() > 0) {
            properties.putAll(initParam);
        }
        this.name = name;
    }

    protected AbstractProcessContext(String name) {
        this.name = name;
    }

    Map<String, Object> getProperties() {
        if (properties == null) {
            properties = Maps.newHashMap();
        }
        return properties;
    }

    @Override
    public Map<String, Object> getUserProperties() {
        if (userProperties == null) {
            userProperties = Maps.newHashMap();
        }
        return userProperties;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Comparable getMatcher() {
        return this.getCvt() == null ? null : this.getCvt().getMatcher();
    }

    Shunt getCvt() {
        return cvt;
    }

    void setCvt(Shunt cvt) {
        this.cvt = cvt;
    }
}