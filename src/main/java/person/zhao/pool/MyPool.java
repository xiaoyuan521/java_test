package person.zhao.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class MyPool extends GenericObjectPool<Person>{

    public MyPool(PooledObjectFactory<Person> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }
    
    public static void main(String[] args) throws Exception{
    }
    

}
