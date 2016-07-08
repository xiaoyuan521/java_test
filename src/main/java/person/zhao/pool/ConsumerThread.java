package person.zhao.pool;

public class ConsumerThread implements Runnable {
    MyPool pool = null;

    public ConsumerThread(MyPool pool) {
        super();
        this.pool = pool;
    }

    @Override
    public void run() {

        Person p = null;
        try {
            p = pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(p.toString());
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        
        pool.returnObject(p);

    }

}
