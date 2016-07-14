package person.zhao.threadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static int CORE_THREAD_NUM = 10;
    private static int MAX_THREAD_NUM = 13;
    private static Long KEEP_ALIVE_TIME = 0L;

    public void base() {
        ExecutorService service =
                new ThreadPoolExecutor(CORE_THREAD_NUM,
                        MAX_THREAD_NUM,
                        KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>()
//                        , new ThreadPoolExecutor.DiscardPolicy()
                );
        
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new ProducerThread(service, "thread - " + i);
            service.submit(runnable);
            sleep(1);
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
        new Main().base();
    }
}
