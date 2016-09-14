package person.zhao.thread;

import java.util.Random;

public class ThreadBase {
    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public int getRandom(int range){
        return new Random().nextInt(range);
    }
}
