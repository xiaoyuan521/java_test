package person.zhao.pool.sub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * 结合了thread pool和object pool来模仿高并发场景的处理 
 */
public class Main {
    
    private static int CORE_THREAD_NUM = 3;
    private static int MAX_THREAD_NUM = 500;
    private static Long KEEP_ALIVE_TIME = 0L;

    public void run(){
        // 对象池设定最大为10，超过10的线程会block
        GenericKeyedObjectPool<String, Connection> keyedPool = new GenericKeyedObjectPool<String, Connection>(new MyPollFactory(), new MyPollConfig());
        // 线程池设定，最大位3， 超过3个会block
        ExecutorService service =
                new ThreadPoolExecutor(CORE_THREAD_NUM,
                        MAX_THREAD_NUM, // 对于LinkedBlockingQueue该设定无意义
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );
        
        // 最后可以看到实际工作的线程，是取的 min(线程池个数，对象池个数)
        for(int i=0;i< 100;i++){
            MyThread t = new MyThread(keyedPool);
            service.submit(t);
        }
    }
    
    public static void main(String[] args){
        try {
            new Main().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyThread implements Runnable{

    GenericKeyedObjectPool<String, Connection> keyedPool = null;
    
    public MyThread(GenericKeyedObjectPool<String, Connection> keyedPool) {
        super();
        this.keyedPool = keyedPool;
    }


    @Override
    public void run() {
        Connection conn = null;
        try {
            // 这里只使用了mysql一个key
            conn = keyedPool.borrowObject("mysql");
            conn.work();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                keyedPool.returnObject("mysql", conn);
            }
        }
    }
    
}

class MyPollConfig extends GenericKeyedObjectPoolConfig {

    public MyPollConfig() {
        super();
        // 最多同时10个线程（每个key下）
        setMaxTotalPerKey(10);
    }
    
}

class MyPollFactory extends BaseKeyedPooledObjectFactory<String, Connection>{

    @Override
    public Connection create(String arg0) throws Exception {
        return new Connection(arg0);
    }

    @Override
    public PooledObject<Connection> wrap(Connection arg0) {
        return new DefaultPooledObject<Connection>(arg0);
    }}


class Connection {
    private String name = null;
    
    public Connection(String name) {
        super();
        this.name = name;
    }

    public void work () throws InterruptedException{
        System.out.println(String.format("[%s] is working", getName()));
        TimeUnit.SECONDS.sleep(2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
} 