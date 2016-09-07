package person.zhao.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlockingQueue
 *
 */
public class Main {

    public void p() {

        List<String> strList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            strList.add(String.valueOf(i));
        }

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(strList);
        // 启动3个线程来分别消费 20 个string，直到全部消费完为止
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new MyThread(queue));
            t.start();
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().p();
    }

}

class MyThread implements Runnable {

    LinkedBlockingQueue<String> queue = null;

    public MyThread(LinkedBlockingQueue<String> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void run() {

        String str = null;
        while (true) {
            str = queue.poll();
            if (str == null) {
                return;
            }

            System.out.println(str);
        }
    }

}
