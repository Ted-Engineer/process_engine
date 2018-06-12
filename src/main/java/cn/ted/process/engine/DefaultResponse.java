package cn.ted.process.engine;

/**
 * @author Ted(lyc)
 * @version : id: DefaultResponse , v 0.1 2018/1/7 15:51 Ted(lyc)
 * @Description
 */
public class DefaultResponse<T> implements Response<T> {

    private boolean result;

    private T       object;

    public DefaultResponse(boolean result, T object) {
        this.result = result;
        this.object = object;
    }

    @Override
    public boolean isResult() {
        return result;
    }

    @Override
    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public T getObject() {
        return object;
    }

    @Override
    public void setObject(T object) {
        this.object = object;
    }
}