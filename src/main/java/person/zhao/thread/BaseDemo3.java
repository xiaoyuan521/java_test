package person.zhao.thread;




/**
 * wait / notify
 * 线程调度
 * 
 * obj.wait()，与obj.notify()必须要与synchronized(obj)一起使用，
 * 也就是wait,与notify是针对已经获取了Obj锁进行操作
 * 
 * obj.wait() -> 当前线程释放obj对象的锁，并且进入等待状态，直到被notify
 * obj.notify() -> 唤醒一个等待该对象锁的线程
 * 
 * 从语法角度来说就是obj.wait(),obj.notify必须在synchronized(obj){...}语句块内。
 *
 */
public class BaseDemo3 {

    public void p() throws Exception {
        
        SingleObj.getInstance();
        
        Thread ta = new Thread(new MyThread3("A"));
        Thread tb = new Thread(new MyThread3("B"));
        Thread tc = new Thread(new MyThread3("C"));
        
        ta.start();
        tb.start();
        tc.start();
        
    }

    public static void main(String[] args) {
        try {
            new BaseDemo3().p();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyThread3 implements Runnable {

    private String name = null;

    public MyThread3(String name) {
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
            output(getName(), i);
        }
    }
    
    private void output(String name, int count){
        SingleObj obj = SingleObj.getInstance();
        synchronized (obj) {
            System.out.println(String.format("[thread %s] - %s", name, String.valueOf(count)));
                try {
                    
                    // 如果把notify放到wait后面，那么nofity就永远得不到执行 ！
                    obj.notifyAll();
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

}

class SingleObj{
    private static SingleObj obj = null;
    private SingleObj(){
    }
    public static SingleObj getInstance(){
        if(obj == null){
            obj = new SingleObj();
        }
        return obj;
    }
}
