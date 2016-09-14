package person.zhao.thread;

import java.io.FileNotFoundException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用Callable + FutureTask来处理线程
 * call方法中抛出异常
 * 
 * 当call方法中抛出异常的时候，
 * 在主线程中需要捕获ExecutionException
 * 从ExecutionException中可以再次取得具体的异常类
 */
public class CallableDemo2 extends ThreadBase {

    public void p() throws InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws FileNotFoundException {
                int a = 1;
                if (a == 1) {
                    throw new FileNotFoundException("no file !");
                }
                return 1;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        Thread t = new Thread(futureTask);
        t.start();

        Integer result = 0;
        try {
            result = futureTask.get();
        } catch (ExecutionException e) {
            System.out.println("catch exception in thred :" + e.getCause().getMessage());
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new CallableDemo2().p();

    }
}
