package person.zhao.threadExecutor;

import java.util.concurrent.ExecutorService;

public class ProducerThread implements Runnable{

    private String name = null;

    public ProducerThread(ExecutorService executeService, String name) {
        super();
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(String.format("I am thread [%s]", this.name));
        sleep(500);
    }
    
    public void sleep(int milisec){
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
