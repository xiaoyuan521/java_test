package person.zhao.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class MyConfig extends GenericObjectPoolConfig {
    private int maxTotal = 3;
    private int maxIdle = 3;
    private int minIdle = 0;

    public MyConfig() {
        setMaxTotal(maxTotal);
        setMaxIdle(maxIdle);
        setMinIdle(minIdle);

        // 设定检测,
        // 调用MyPoolObjectFactory的validateObject方法进行检测，
        // 检测结果为false的时候，会将对象移出池，并重新创建。
        setTestOnReturn(true);
//        setTestOnBorrow(true);
        // setTestWhileIdle(true);
    }
}
