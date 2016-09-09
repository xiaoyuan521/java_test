package person.zhao.thread;

import java.lang.Thread.UncaughtExceptionHandler;


public class BaseDeomException {

    /**
     * 调用run和start启动线程（Thread）
     * @throws InterruptedException
     */
    public void p() throws InterruptedException {
        System.out.println("111111");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sleep(1000);
                System.out.println("2222222");
            }
        };
        
        Thread t = new Thread(runnable);
        t.start();
        t.join();

        System.out.println("33333");
    }
    
    /**
     * 调用run和start启动线程的区别（Runnable）
     */
    public void p2() {
        System.out.println("111111111");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("2222");
            }
        };
        runnable.run();
        System.out.println("33333333");
    }
    
    /**
     * Exception in Thread
     */
    public void p3() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("2222");
                throw new RuntimeException("runtime exception !");
            }
        };
        new Thread(runnable).start();
    }
    
    /**
     * Exception in Thread
     * 通过Thread.setUncaughtExceptionHandler方法，可以接收到Thread中的Runtime的exception
     * 需要注意的是handler的执行是在子线程中。
     * 
     */
    public void p4() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("2222");
                throw new RuntimeException("runtime exception !");
            }
        };
        Thread t = new Thread(runnable);
        t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("get RuntimeException in Thread !");
            }
        });

        t.start();
    }

    public static void main(String[] args) throws Exception {
        try{
            new BaseDeomException().p4();
            System.out.println("in main");
        }catch(Exception e){
            System.out.println("catch erro in main thread !");
        }
    }

    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
