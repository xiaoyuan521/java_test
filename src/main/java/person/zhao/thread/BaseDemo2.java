package person.zhao.thread;

/**
 * join
 * 
 * 在A线程中调用B线程的join
 * 相当于把A线程设定为阻塞状态， B线程执行，B执行完毕，A继续执行
 * 
 * 使用join阻塞A线程，不会使A线程释放锁
 * 这点与wait不一样， 与sleep一样。
 * 
 * @author zhao_hongsheng
 *
 */
public class BaseDemo2 {

    public void p() {
        
        System.out.println("-- main begin --");
        
        Thread ta = new Thread(new MyThread2("A"));
        Thread tb = new Thread(new MyThread2("B"));
        
        ta.start();
        tb.start();
        
        try {
            ta.join();
            tb.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("-- main end --");
    }

    public static void main(String[] args) {
        new BaseDemo2().p();
    }
}

class MyThread2 implements Runnable {

    private String name = null;

    public MyThread2(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("[thread %s] - %s", getName(), String.valueOf(i)));
        }
    }

}
