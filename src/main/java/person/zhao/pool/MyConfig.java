package person.zhao.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class MyConfig extends GenericObjectPoolConfig{
    private int maxTotal = 3;
    private int maxIdle = 3;
    private int minIdle = 0;
    
    public MyConfig() {
        setMaxTotal(maxTotal);
        setMaxIdle(maxIdle);
        setMinIdle(minIdle);
    }
}
