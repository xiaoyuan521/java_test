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
            
            // 从池中取得对象
            p = pool.borrowObject();
            
            // 模拟随机出现的异常，几率 10%
            // 出错的场合， catch exception， 然后， invalidateObject
            randomThrowRuntimeException();

            // 对象的使用
            System.out.println(p.toString());
            sleep(500);
        } catch (Exception e) {
            try {
                pool.invalidateObject(p);
            } catch (Exception invalidObjectException) {
                invalidObjectException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if(p != null){
                pool.returnObject(p);
            }else {
                System.out.println("object is null , can not return to pool !");
            }
        }
        
    }

    private void sleep(int milisec) {
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void randomThrowRuntimeException(){
        int randomInt = (int)(Math.random() * 10);
        if(randomInt == 9){
            throw new RuntimeException("Random runtime exception .");
        }
    }

}
