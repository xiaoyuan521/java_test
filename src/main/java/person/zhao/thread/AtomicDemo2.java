package person.zhao.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这个类使用atomicInteger，
 * 10000线程进行 +1 动作，
 * 结果保证为10000
 *
 */
public class AtomicDemo2 {

    public void p() throws InterruptedException{
        List<Thread> tList = new ArrayList<Thread>();
        
        // 启动10个线程，进行 +1 操作
        for(int i=0;i<10000;i++){
            Thread t = new Thread(new AdderAtom());
            t.start();
            tList.add(t);
        }
        
        // 主线程等待子线程结束
        for(Thread t: tList){
            t.join();
        }
        
        System.out.println(CountHolderAtomic.getInstance().getCount());
    }
    
    public static void main(String[] args) throws Exception{
        new AtomicDemo2().p();
    }
}

class AdderAtom implements Runnable{

    @Override
    public void run() {
        
        CountHolderAtomic ch = CountHolderAtomic.getInstance();
        ch.add(1);
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

class CountHolderAtomic {
    private static CountHolderAtomic instance = new CountHolderAtomic();
    private AtomicInteger count = new AtomicInteger(0);
    private CountHolderAtomic(){
    }
    public static CountHolderAtomic getInstance(){
        return instance;
    }
    public int getCount() {
        return count.get();
    }
    public int add(int i) {
        return count.addAndGet(i);
    }
}
