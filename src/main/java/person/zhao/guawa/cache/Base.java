package person.zhao.guawa.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class Base {

    public void p() throws InterruptedException {
        LoadingCache<Integer, Person> personCache =
                CacheBuilder.newBuilder()
                        .concurrencyLevel(5)
                        .expireAfterAccess(150, TimeUnit.SECONDS)
                        .maximumSize(15)
                        .initialCapacity(2)
                        .recordStats()
                        .removalListener(new RemovalListener<Integer, Person>() {

                            @Override
                            public void onRemoval(RemovalNotification<Integer, Person> notification) {
                                System.out.println(String.format("[remove] %s, age is: %s", notification
                                        .getKey(), String.valueOf(notification.getValue().getAge())));
                            }
                        })
                        .build(new CacheLoader<Integer, Person>() {

                            @Override
                            public Person load(Integer key) throws Exception {
                                return new Person("default person", 30);
                            }
                        });
        
        Thread producerThread = new Thread(new Producer(personCache));
        producerThread.join();
        producerThread.start();
        
        Thread consumerThread = new Thread(new Consumer(personCache));
        consumerThread.join();
        consumerThread.start();

    }

    public static void main(String[] args) throws Exception {
        new Base().p();
    }
}

class Consumer implements Runnable {

    private LoadingCache<Integer, Person> cache = null;

    public Consumer(LoadingCache<Integer, Person> cache) {
        super();
        this.cache = cache;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            try {
                Person p = cache.get(new Integer(i));
                System.out.println(String.format("get one person from cache [%s]", p.getName()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer implements Runnable {
    
    LoadingCache<Integer, Person> cache = null;
    
    public Producer(LoadingCache<Integer, Person> cache) {
        super();
        this.cache = cache;
    }

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            Person p = new Person("name" + i, i);
            cache.put(new Integer(i),p);
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

}

class Person {
    private String name = null;
    private int age = 0;

    public Person() {
        super();
    }

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
