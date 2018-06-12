package cn.ted.process.engine.shunt;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ted(lyc)
 * @version : id: InjectedShunt , v 0.1 2018/1/2 13:32 Ted(lyc)
 * @Description 分流器
 */
public class InjectedShunt<Matcher extends Comparable<Matcher>, Entity> implements Shunt<Matcher, Entity> {

    private Matcher                 matcher;

    private Entity                  entity;

    private HashMap<String, Object> params = Maps.newHashMap();

    public InjectedShunt(Matcher matcher) {
        this.matcher = matcher;
    }

    public InjectedShunt(Matcher matcher, Entity entity) {
        this.matcher = matcher;
        this.entity = entity;
    }

    @Override
    public int shunt(Object condition) {
        if (condition == null) {
            return 1;
        }
        if (!condition.getClass().equals(this.matcher.getClass())) {
            throw new IllegalArgumentException("[InjectedShunt] condition: " + condition + " type is: " + condition.getClass() + " is not same with matcher : " + this.matcher
                                               + " type: " + this.matcher.getClass());
        }
        return ((Matcher) condition).compareTo((Matcher) this.matcher);
    }

    @Override
    public Matcher getMatcher() {
        return this.matcher;
    }

    @Override
    public Entity getResult() {
        return this.entity;
    }

    @Override
    public Shunt setResult(Entity entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public Shunt addInjectedParam(String key, Object val) {
        this.params.put(key, val);
        return this;
    }

    @Override
    public Map<Object, Object> getInjectedParam() {
        return Collections.unmodifiableMap(this.params);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InjectedShunt{");
        sb.append("matcher=").append(matcher);
        sb.append(", entity=").append(entity);
        sb.append('}');
        return sb.toString();
    }
}