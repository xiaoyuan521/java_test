package person.zhao.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class Main {
    
    public void p(){
        
        PooledObjectFactory<Person> factory = new MyPoolObjectFactory();
        GenericObjectPoolConfig config = new MyConfig();
        MyPool pool = new MyPool(factory, config);
        
        /**
         * MyConfig中设定， 池中的最大的连接数为3
         * 
         * 起20个线程，每到3个的时候，对象池中的对象耗尽，等待归还后才能继续使用
         * 所以，输出3个对象， 阻塞1秒，输出3个对象， 阻塞1秒, ....
         */
        for(int i=0;i< 20;i++){
            new Thread(new ConsumerThread(pool)).start();
        }
    }
    
    public static void main(String[] args){
        new Main().p();
    }

}
