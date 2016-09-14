package person.zhao.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用Callable + FutureTask来处理线程
 * FutureTask.get方法会阻塞当前线程。
 */
public class CallableDemo extends ThreadBase{

    public void p() throws InterruptedException, ExecutionException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("in callable demo ~");
                sleep(1000);
                return 1;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        Thread t = new Thread(futureTask);
        t.start();
        
        System.out.println("in main 111~");
        System.out.println(futureTask.get() + "");
        System.out.println("in main 222 ~");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new CallableDemo().p();
    }
}
