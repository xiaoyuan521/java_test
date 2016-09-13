package person.zhao.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类没有使用atomicInteger，
 * 10000线程进行 +1 动作，
 * 结果有一定几率不能为10000。
 */
public class AtomicDemo {

    public void p() throws InterruptedException{
        List<Thread> tList = new ArrayList<Thread>();
        
        // 启动10个线程，进行 +1 操作
        for(int i=0;i<10000;i++){
            Thread t = new Thread(new Adder());
            t.start();
            tList.add(t);
        }
        
        // 主线程等待子线程结束
        for(Thread t: tList){
            t.join();
        }
        
        System.out.println(CountHolder.getInstance().getCount());
    }
    
    public static void main(String[] args) throws Exception{
        new AtomicDemo().p();
    }
}

class Adder implements Runnable{

    @Override
    public void run() {
        
        CountHolder ch = CountHolder.getInstance();
        // 随机sleep 1 毫秒， 模拟实际场景
//        simulateSlowCalc();
        ch.setCount(ch.getCount() + 1);
    }
    
    public void simulateSlowCalc(){
        int sleepTime = (int)(Math.random() * 2);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CountHolder {
    private static CountHolder instance = new CountHolder();
    private int count = 0;
    private CountHolder(){
    }
    public static CountHolder getInstance(){
        return instance;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
