package person.zhao.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用Callable + ExecutorService的例子
 * 多线程异步执行，并且取得返回结果
 * 返回结果按照线程加入service的顺序取得。
 * 
 * @author zhao_hongsheng
 *
 */
public class CallableExecutorDemo {

    public void p() throws InterruptedException, ExecutionException {

        List<Future<String>> futures = new ArrayList<Future<String>>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Callable<String> callable = null;

        for (int i = 0; i < 10; i++) {
            callable = new MyCallable(String.valueOf(i));
            futures.add(executor.submit(callable));
        }

        executor.shutdown();

        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
        
        System.out.println("all done ...");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new CallableExecutorDemo().p();
    }
}

class MyCallable extends ThreadBase implements Callable<String>{
    private String name = null;
    
    public MyCallable(String name) {
        super();
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        int sleepTime = getRandom(1000);
        sleep(sleepTime);
        return String.format("I am thread [%s], I slept [%s]", getName(), String.valueOf(sleepTime));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}