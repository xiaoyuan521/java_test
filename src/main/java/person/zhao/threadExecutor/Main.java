package person.zhao.threadExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static int CORE_THREAD_NUM = 10;
    private static int MAX_THREAD_NUM = 13;
    private static Long KEEP_ALIVE_TIME = 0L;

    /**
     * 使用SynchronousQueue
     * pool的最大容量设定为了Integer.MAX_VALUE。
     */
    public void p1() {
        ExecutorService service =
                new ThreadPoolExecutor(CORE_THREAD_NUM,
                        Integer.MAX_VALUE,
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>()
                );
        
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new ProducerThread(service, "thread - " + i);
            service.submit(runnable);
            sleep(1);
        }
    }
    
    /**
     * 使用LinkedBlockingQueue
     * pool的最大容量设定没有实际意义，
     * 超过pool.core容量的线程，都存在了LinkedBlockingQueue中
     */
    public void p2() {
        ExecutorService service =
                new ThreadPoolExecutor(CORE_THREAD_NUM,
                        MAX_THREAD_NUM,
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );
        
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new ProducerThread(service, "thread - " + i);
            service.submit(runnable);
            sleep(1);
        }
    }
    
    /**
     * 使用ArrayBlockingQueue
     * pool.core = 10, pool.max = 13, ArrayBlockingQueue.size = 20
     * 13 + 20 = 33
     * 
     * 超过33的部分，使用RejectPolicy
     * 默认是抛异常，已可以丢弃
     */
    public void p3() {
        ExecutorService service =
                new ThreadPoolExecutor(CORE_THREAD_NUM,
                        MAX_THREAD_NUM,
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(20)
                // ,new ThreadPoolExecutor.DiscardOldestPolicy()
                );
        try {
            for (int i = 0; i < 100; i++) {
                Runnable runnable = new ProducerThread(service, "thread - " + i);
                service.submit(runnable);
                sleep(1);
            }
        } finally {
            service.shutdown();
        }
    }
    
    /**
     * 使用apache提供的Executors.newSingleThreadExecutor
     * 保证thread串行执行
     */
    public void p4(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 100; i++) {
                Runnable runnable = new ProducerThread(service, "thread - " + i);
                service.submit(runnable);
                sleep(1);
            }
        } finally {
            service.shutdown();
        }
    }
    
    public void sleep(int milisec){
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().p4();
    }
}
