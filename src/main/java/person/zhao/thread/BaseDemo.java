package person.zhao.thread;

/**
 * 最简单的例子，继承Runnable接口，实现多线程
 * @author zhao_hongsheng
 *
 */
public class BaseDemo {

    public void p() {
        Thread ta = new Thread(new MyThread("A"));
        Thread tb = new Thread(new MyThread("B"));
        ta.start();
        tb.start();
    }

    public static void main(String[] args) {
        new BaseDemo().p();
    }
}

class MyThread implements Runnable {

    private String name = null;

    public MyThread(String name) {
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
