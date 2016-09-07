package person.zhao.threadExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TerminateSenseThreadExecutor extends ThreadPoolExecutor {

    TerminateCallback callback = null;

    public TerminateSenseThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, TerminateCallback callback) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.callback = callback;
    }

    @Override
    protected void terminated() {
        super.terminated();
        callback.terminate();
    }

    public static ThreadPoolExecutor Executor(int nThreads, TerminateCallback callback) {

        // apache Executors.newFixedThreadPoolを参照する
        return new TerminateSenseThreadExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), callback);
    }

}
