package person.zhao.thread;

/**
 * synchonized synchonize可以放在方法上，也可以同步方法块
 * 无论修饰方法还是对象（方法块）, 锁定的都是调用该方法的对象 
 * 
 * 一个对象有多个synchonized方法或者方法块，
 * 调用一个其他的就都锁定 
 * 原理见上面一段儿
 * 
 * 不同实例的sychonized方法互不影响
 * static的sychonized方法则相反，作用在所有的实例上 
 * 
 * static和非static方法的锁互不干预，一个锁的是Class，一个锁的是obj
 * synchonized函数可以被继承，但是synchnized关键字不被继承
 */
public class BaseDemo4 {

    public void p() throws Exception {

        Object obj1 = new Object();
        Object obj2 = new Object();

        // 线程A,B是使用相同的对象锁，A，B串行
        // 线程C是不同对象，C和A竞争
        Thread ta = new Thread(new MyThread41("A", obj1));
        Thread tb = new Thread(new MyThread41("B", obj1));
        Thread tc = new Thread(new MyThread41("C", obj2));

        ta.start();
        tb.start();
        tc.start();
    }

    public static void main(String[] args) {
        try {
            new BaseDemo4().p();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyThread41 implements Runnable {

    private String name = null;
    private Object obj = null;


    public MyThread41(String name, Object obj) {
        super();
        this.name = name;
        this.obj = obj;
    }


    @Override
    public void run() {
        // synchorized放在for上面和for下面会有不同的现象， 有意思。。。， 看来synchronize块结束，是有机会释放锁的
        // thread C存在与否也对A B的结果有影响。 有意思。。。， 看来有竞争也会导致释放锁的时机不同。
        synchronized (obj) {
            for (int i = 1; i <= 10; i++) {
                System.out.println(String.format("[thread %s] - %s", name, String.valueOf(i)));
            }
        }
    }

}
