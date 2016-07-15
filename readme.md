
## 目录

* [junit](https://github.com/yisuren/java_test#junit)
* [Apache common Pool](https://github.com/yisuren/java_test#apache-common-pool)
* [Thread pool Executor](https://github.com/yisuren/java_test#thread-pool-executor)
* [thread](https://github.com/yisuren/java_test#thread)

---


#### junit

* junit 4.x

	如果低于4版本，下面有些方法是不支持的  
	例如，执行特定类的特定方法。

* 继承`junit.framework.TestCase class`

* 概念

	junit执行会为每一个@Test（testXXX）方法重新生成一个instance  
	所以各个junit方法之间不能传递和分享状态  
	每个测试方法前执行setup，方法后执行teardown  
	[http://etutorials.org/Programming/Java+extreme+programming/Chapter+4.+JUnit/4.6+Set+Up+and+Tear+Down/](http://etutorials.org/Programming/Java+extreme+programming/Chapter+4.+JUnit/4.6+Set+Up+and+Tear+Down/)


* 使用了surface plugin

	提供junit测试的report  
	提供各种测试方法  
	[https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html](https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html)  

* 只执行一次的setup和teardown怎么写？

	[参考](http://etutorials.org/Programming/Java+extreme+programming/Chapter+4.+JUnit/4.7+One-Time+Set+Up+and+Tear+Down/)

* 如何用suit组织你的代码，包括不同测试类的特定方法的组合

	[参考](http://etutorials.org/Programming/Java+extreme+programming/Chapter+4.+JUnit/4.8+Organizing+Tests+into+Test+Suites/)

* 执行全部

	mvn test

* 执行一个测试类

	mvn -Dtest=AppTest test

* 执行某个类的特定方法

	mvn -Dtest=AppTest#testA test

* 使用suit方法组合多个测试类
```
public static Test suite() {
    return new TestSuite(GenericMRTest.class, OtherClass.class);
}
```

---

#### Apache common Pool

* 对象池
* 常用的是GenericeObjectPool, 创建需要一个Factory， 一个config
* 使用对象的时候，从pool中borrow，使用完return
* 异常处理  
  例如borrow对象后，使用对象进行处理，无论是否出异常，需要把对象return回到pool  
  例如pool中的对象是一个connection，如果connection断开了，需要将对象移出pool（testOnReturn等）
* 常用的是GenericeObjectPool对象， 它是线程安全的

---

#### Thread pool Executor

* 有什么用处？

	> Thread pools address two different problems: they usually provide improved performance when executing large numbers of asynchronous tasks, due to reduced per-task invocation overhead, and they provide a means of bounding and managing the resources, including threads, consumed when executing a collection of tasks. Each ThreadPoolExecutor also maintains some basic statistics, such as the number of completed tasks.

 	1. 解决大量并发线程的性能问题  

 	2. 维护一些统计信息，例如完成的task数，一些hooks方法（before， after）

* 中文资料，简单理解

	[http://825635381.iteye.com/blog/2184680](http://825635381.iteye.com/blog/2184680)

* API DOC

	[http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ThreadPoolExecutor.html](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ThreadPoolExecutor.html)

* 3种基本的队列

	> Direct handoffs. A good default choice for a work queue is a SynchronousQueue that hands off tasks to threads without otherwise holding them. Here, an attempt to queue a task will fail if no threads are immediately available to run it, so a new thread will be constructed. This policy avoids lockups when handling sets of requests that might have internal dependencies. Direct handoffs generally require unbounded maximumPoolSizes to avoid rejection of new submitted tasks. This in turn admits the possibility of unbounded thread growth when commands continue to arrive on average faster than they can be processed.

	SynchronousQueue, queue本身没有容量，期望设定无限的maxpoolsize，这种设定允许pool不断的增大

	> Unbounded queues. Using an unbounded queue (for example a LinkedBlockingQueue without a predefined capacity) will cause new tasks to wait in the queue when all corePoolSize threads are busy. Thus, no more than corePoolSize threads will ever be created. (And the value of the maximumPoolSize therefore doesn't have any effect.) This may be appropriate when each task is completely independent of others, so tasks cannot affect each others execution; for example, in a web page server. While this style of queuing can be useful in smoothing out transient bursts of requests, it admits the possibility of unbounded work queue growth when commands continue to arrive on average faster than they can be processed.

	LinkedBlockingQueue, queue本身是无界的，pool的容量保持一定，不会增加，maxpoolsize的设定没有意义，因为多余的thread都保存在了LinkedBlockingQueue中。

	> Bounded queues. A bounded queue (for example, an ArrayBlockingQueue) helps prevent resource exhaustion when used with finite maximumPoolSizes, but can be more difficult to tune and control. Queue sizes and maximum pool sizes may be traded off for each other: Using large queues and small pools minimizes CPU usage, OS resources, and context-switching overhead, but can lead to artificially low throughput. If tasks frequently block (for example if they are I/O bound), a system may be able to schedule time for more threads than you otherwise allow. Use of small queues generally requires larger pool sizes, which keeps CPUs busier but may encounter unacceptable scheduling overhead, which also decreases throughput.

	ArrayBlockingQueue， queue的容量一定

* apache提供的几个已经实现好了的executor

	* Executors.newCachedThreadPool() (unbounded thread pool, with automatic thread reclamation)
	* Executors.newFixedThreadPool(int) (fixed size thread pool)
	* Executors.newSingleThreadExecutor() (single background thread),

#### Thread

* 3中阻塞状态（blocking）
	* 等待阻塞：运行的线程执行wait()方法，JVM会把该线程放入等待池中。(wait会释放持有的锁)
	* 同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。
	* 其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。（注意,sleep是不会释放持有的锁）