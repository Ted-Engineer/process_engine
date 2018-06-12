package cn.ted.process.engine;

/**
 * @author Ted(lyc)
 * @version : id: Response , v 0.1 2018/1/7 15:49 Ted(lyc)
 * @Description
 */
public interface Response<T> {

    /**
     * 结果成功失败
     * @return
     */
    boolean isResult();

    /**
     * 设置执行成功失败
     * @param result
     */
    void setResult(boolean result);

    /**
     * 执行结果
     * @return
     */
    T getObject();

    /**
     * 设置执行结果
     * @param t
     */
    void setObject(T t);
}