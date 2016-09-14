package person.zhao.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用Callable + ExecutorService + CompletionService的例子
 * 多线程异步执行，并且取得返回结果
 * 返回结果按照线程完成的优先顺序。
 * 
 * @author zhao_hongsheng
 *
 */
public class CallableExecutorDemo2 {

    public void p() throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletionService<String> completionExecutor = new ExecutorCompletionService<String>(executor);
        Callable<String> callable = null;

        for (int i = 0; i < 10; i++) {
            callable = new MyCallable(String.valueOf(i));
            completionExecutor.submit(callable);
        }

        executor.shutdown();

        for (int i = 0; i < 10; i++) {
            System.out.println(completionExecutor.take().get());
        }
        
        System.out.println("all done ...");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new CallableExecutorDemo2().p();
    }
}
