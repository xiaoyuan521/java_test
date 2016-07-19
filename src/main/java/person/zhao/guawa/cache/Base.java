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
                        .concurrencyLevel(5) // 最大并发线程
                        .expireAfterAccess(150, TimeUnit.SECONDS) // 最大缓存时间
                        .maximumSize(15) // 缓存的size
                        .initialCapacity(2)
                        .recordStats() // 记录统计信息

                        // 元素从cache中被移除时候，调用这个方法
                        .removalListener(new RemovalListener<Integer, Person>() {
                            @Override
                            public void onRemoval(RemovalNotification<Integer, Person> notification) {
                                Person p = notification.getValue();
                                System.out.println(String.format("[remove] %s, age is: %s", p.getName(), p.getAge()));
                            }
                        })

                        // 如果缓存未命中，会执行load方法产生一个对象
                        // load可以返回一个特殊的值，比如null或者 -1之类的，代表没有缓存对象
                        .build(new CacheLoader<Integer, Person>() {
                            @Override
                            public Person load(Integer key) throws Exception {
                                return new Person("default person", 30);
                            }
                        });

        // 生产者线程，生产10个对象
        Thread producerThread = new Thread(new Producer(personCache));
        producerThread.start();
        producerThread.join();

        // 消费者线程，每0.5秒消费一个对象
        Thread consumerThread = new Thread(new Consumer(personCache));
        consumerThread.start();
        consumerThread.join();
        
        // 输出统计信息
        System.out.println(personCache.stats());
        System.out.println(String.format("hit rate is [%.2f%%]", personCache.stats().hitRate() * 100));

    }

    public static void main(String[] args) throws Exception {
        new Base().p();
    }
}

/**
 * 生产者
 * @author zhao_hongsheng
 *
 */
class Producer implements Runnable {

    LoadingCache<Integer, Person> cache = null;

    public Producer(LoadingCache<Integer, Person> cache) {
        super();
        this.cache = cache;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            Person p = new Person("name" + i, i);
            cache.put(new Integer(i), p);
        }
    }

}

/**
 * 消费者
 * @author zhao_hongsheng
 *
 */
class Consumer implements Runnable {

    private LoadingCache<Integer, Person> cache = null;

    public Consumer(LoadingCache<Integer, Person> cache) {
        super();
        this.cache = cache;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 20; i++) {
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
