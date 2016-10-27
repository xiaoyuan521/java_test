
## 目录

* [java命令行](#java命令行)
* [设计模式](#设计模式)
* [junit](#junit)
* [Apache common Pool2](#apache-common-pool2)
* [c3p0](#c3p0)
* [Thread pool Executor](#thread-pool-executor)
* [Thread](#thread)
* [java local cache](#java-local-cache)
* [maven](#maven)
* [分布式处理](#分布式处理)
* [java资源文件](#资源文件)
* [java byte型](# byte)

---

#### java命令行

* 原理

	java虚拟机加载已经编译好的class文件，然后运行

* 基本方法 
	
	java -cp /usr/local/xx.jar;xxx.jar;... -Dsystem.env.somepath=/usr/local -Xmx 1024m package.sub.ClassName

* java class加载顺序

1. Bootstrap classes - 虚拟机自带jar包 - System.getProperty("sun.boot.class.path")

2. Extension classer - %JAVA_HOME\jre\lib\ext - System. getProperty("java.ext.dirs”)

3. User classes - 用户自定义jar包 - System.getProperty("java.class.path")

	搜索顺序为： *当前目录* -> *环境变量 CLASSPATH* -> *参数 -cp指定*

* 参数说明

	* -cp, -classpath

		告知虚拟机搜索目录名、jar 文档名、zip 文档名，之间用分号;分隔（linux用 : ）。  
		使用-classpath 后虚拟机将 _不再_ 使用 环境变量CLASSPATH 中的类搜索路径。  
		推荐使用 -cp， 或者局部的（shell内）CLASSPATH，而不推荐jar设定到全局的CLASSPATH中。

	* `-D<propertyName>=value`

		在虚拟机的系统属性中设置属性名/值对，运行在此虚拟机之上的应用程序可用。

---

#### 设计模式

* 设计模式的6个原则  
[http://www.uml.org.cn/sjms/201211023.asp](http://www.uml.org.cn/sjms/201211023.asp)

 	* 单一职责原则

		没什么需要多说的  
		注意点： 修正既存的代码的时候，很容易职责扩散， 注意及时重构

	* 里氏替换原则

		子类不重写抽象中已经实现好的方法。

	* 依赖倒置原则

		面向接口编程， 高层对象不依赖于底层的对象。  
		（底层对象修正高层对象不受到影响）

	* 接口隔离原则

		相当于接口的职责单一化，每个接口中只定义必须的方法。  

	* 迪米特法则

		每个对象都尽量少的依赖其他对象。

	* 开闭原则

		对扩展开放，都修改封闭。  
		例如，增加一项新功能，如果能通过新建文件扩展某个类实现，就符合原则  
		如果必须通过修正现有代码来实现，就不符合原则

* 代码示例  
[参考](http://www.cnblogs.com/maowang1991/archive/2013/04/15/3023236.html)

	* 工厂模式

		简单 / 静态 / 抽象
	
	* 单利模式  

		懒汉 / 饿汉 / 线程安全  
		[http://www.jb51.net/article/46922.htm](http://www.jb51.net/article/46922.htm)
	
	* 模板（template）模式

	* 装饰器模式 / 代理模式 / 匿名内部类

* 感受

	* 设计模式的根本目的是为了，降低耦合，提高复用，减少重复代码，方便代码维护。
	* 很多设计模式方法都是很类似的，只是应用场景不同，或者类之间的关联方法不同。
	* 设计模式并不能解决所有问题。
	* 在实际的编程中或多或少我们都无意中应用过。
	* 实际应用各种无需刻意最求使用某种设计模式，但是要符合设计模式的6个原则。

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

#### Apache common Pool2

* 对象池
* 常用的是GenericeObjectPool, 创建需要一个Factory， 一个config
* 使用对象的时候，从pool中borrow，使用完return
* 异常处理  
  例如borrow对象后，使用对象进行处理，无论是否出异常，需要把对象return回到pool  
  例如pool中的对象是一个connection，如果connection断开了，需要将对象移出pool（testOnReturn等）
* 常用的是GenericeObjectPool对象， 它是线程安全的

---

#### c3p0

* 官网

 http://www.mchange.com/projects/c3p0/index.html#using_c3p0

* 用法

 参考代码

---

#### Thread pool Executor

* 有什么用处？

	> Thread pools address two different problems: they usually provide improved performance when executing large numbers of asynchronous tasks, due to reduced per-task invocation overhead, and they provide a means of bounding and managing the resources, including threads, consumed when executing a collection of tasks. Each ThreadPoolExecutor also maintains some basic statistics, such as the number of completed tasks.

 	1. 解决大量并发线程的性能问题  
 	另一种说法： 线程池的作用就是将线程进行复用，而不是把性能都浪费在线程的启动和切换的开销中。

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
	* Executors.newSingleThreadExecutor() (single background thread)

* `service.shutdown`

	* 调用`shutdonw`方法会是现有的service不能再加入更多的thread，已经加入的service会正确执行完成。
	* `shutdownNow` 能立即停止所有线程
	* 如果不调用 `service.shutdown` 方法，主线程不停止。

* service的回调方法

	* 每个线程的before， after: 继承 `ThreadPoolExecutor`, 扩展方法 `beforeExecute` `afterExecute`
	* 所有线程执行完了的callback 继承`terminated`方法

---

#### Thread

* 3种阻塞状态（blocking）
	* 等待阻塞：运行的线程执行wait()方法，JVM会把该线程放入等待池中。(wait会释放持有的锁)
	* 同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。
	* 其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。（注意,sleep是不会释放持有的锁）

* 文章

	* [http://www.mamicode.com/info-detail-517008.html](http://www.mamicode.com/info-detail-517008.html)
	* 并发系列文章，好！  
	[https://my.oschina.net/hosee/blog/597934](https://my.oschina.net/hosee/blog/597934)

* run和start的对比

 * 调用thread#run方法，并不会启动一个新的线程，而是相当于在主线程中，直接调用
 * 调用thread#start方法，会启动一个新的线程
 * thread.start + join 方法，能够让主线程等待子线程结束，再结束

* 子线程的异常处理

 * 子线程不应该抛出任何unchecked的Exception （通过run 方法的签名不允许抛出异常）
 * 子线程中的异常应该在自己内部解决（run 方法内）
 * 子线程抛出的Runtime Exception， 主线程无法捕获（catch）
 * 主线程可以通过设定Thread.setUncaughtExceptionHandler来处理Runtime Exception  
 要注意，handler的执行是在子线程中。
 * 使用 Callable 可以在主线程中捕获到子线程的异常  
 参考代码： [https://github.com/yisuren/java_test/blob/master/src/main/java/person/zhao/thread/CallableDemo2.java](https://github.com/yisuren/java_test/blob/master/src/main/java/person/zhao/thread/CallableDemo2.java)

* 线程的生命周期

 * 一般来说，主线程会等待子线程的结束自己再结束  
 
  * 如果主线程调用了 `System.exit(returnCode);` 强行结束，那么不会等待子线程
  * 如果子线程使用了 t.join, 加入到了主线程中，那么主线程会等待子线程。
 
* 无锁

 * AtomicInteger 属于无锁类，能解决线程数据的同步问题
 * 无锁类的效率要高于同步（synchonized）
 * 无锁类的原理是： 相互竞争的线程总有一个会成功，其他不成功的会再次执行。

* 各种同步对象

 * Map m = Collections.synchronizedMap(new HashMap())，能将一个普通的map变成同步的map  
  原理就是将map中的每个方法都加上synchonized关键字

 * ConcurrentHashMap 是专门提供的同步map，效率要比上面这个要快的多

 * ArrayBlockingQueue, LinkedBlockingQueue

		* BlockingQueue不是一个高性能的容器。但是它是一个非常好的共享数据的容器。是典型的生产者和消费者的实现。

		* 2个都是线程安全的，内部一个是array存储，一个是链表存储（猜测）
 
  		* 常用方法和说明 [http://blog.csdn.net/zhuyijian135757/article/details/41623637](http://blog.csdn.net/zhuyijian135757/article/details/41623637)
 
  		* 由于都是线程安全的，可以用来进行多线程编程  
		最简单 生产者 / 消费者 线程模型  
  		代码参考: [https://github.com/yisuren/java_test/blob/master/src/main/java/person/zhao/queue/Main.java](https://github.com/yisuren/java_test/blob/master/src/main/java/person/zhao/queue/Main.java)

* Runnable, Callable
 
 * Callable规定的方法是call(),Runnable规定的方法是run().
 * Callable的任务执行后可返回值，而Runnable的任务是不能返回值得
 * call方法可以抛出异常，run方法不可以
 * 运行Callable任务可以拿到一个Future对象，表示异步计算的结果。
 * Callable不能直接作为线程启动，可以结合FutureTask类来启动线程，也可以直接submit到ExecutorService中启动

* java.util.concurrent.Future
 
 * Future类是用来接收异步调用的结果的
 * Future.get方法能取得异步调用个结果，如果结果还未返回，这个方法会阻塞当前线程，直到结果返回

* CompletionService

 * 如果需要取得多个并行线程的结果，可以使用CompletionService.take().get()
 * CompletionService.take().get() 需要循环调用，结果返回的顺序为线程完成的先后顺序。

---

#### Java local cache

* 什么是缓存

	为了提高系统性能而专门开辟的一块高速空间。

* 缓存有哪些种类

	缓存有很多种  

		CPU缓存  
		操作系统缓存  
		本地缓存  
		HTTP缓存  
		等等

* Guawa CacheBuilder

	是goolge实现的Java本地缓存策略  
	设定丰富， 线程安全， 是java本地缓存的不二选择

	特点

		* Simple, in-memory caching
		* Thread-safe implementation (internally similar to ConcurrentHashMap)
		* No explicit support for distributed caching

* 参考

	中文: [http://www.cnblogs.com/peida/p/Guava_Cache.html](http://www.cnblogs.com/peida/p/Guava_Cache.html)  
	官方pdf（需要科学上网）: [https://guava-libraries.googlecode.com/files/JavaCachingwithGuava.pdf](https://guava-libraries.googlecode.com/files/JavaCachingwithGuava.pdf) 

---

#### maven

* maven dependency scope的说明

	* compile  
	默认的scope，表示 dependency 都可以在生命周期中使用。而且，这些dependencies 会传递到依赖的项目中。适用于所有阶段，会随着项目一起发布

	* provided  
	跟compile相似，但是表明了dependency 由JDK或者容器提供，例如Servlet AP和一些Java EE APIs。这个scope 只能作用在编译和测试时，同时没有传递性。

	* runtime  
	表示dependency不作用在编译时，但会作用在运行和测试时，如JDBC驱动，适用运行和测试阶段。

	* test  
	表示dependency作用在测试时，不作用在运行时。 只在测试时使用，用于编译和运行测试代码。不会随项目发布。

	* system  
	跟provided 相似，但是在系统中要以外部JAR包的形式提供，maven不会在repository查找它。  
	必须指定systemPath，慎用

* java 依赖包的理解

	* 编译时需要的包，运行时也一定需要
	* 编译时不需要的包，运行时可能需要，例如JDBC驱动  
	基于接口的实现,class.forName("xxx")加载等。

* Maven加速

	* settings.xml的说明

		`settings.xml`在maven的安装目录下有一个总的，通常这个目录页是MAVEN_HOME  
		另外在各个用户的目录下，每个用户还有一个`settings.xml`  
		位置 `/root/.m2/setting.xml`，如果没有这个文件可以从MAVEN_HOME/conf下拷贝一个

	* maven加速的设定

		maven默认连的central仓库的速度比较慢，  
		为了提高速度可以设定速度好的mirror，在`settings.xml`中，找到mirrors内设定
		```
		<mirror>    
		      <id>repo2</id>    
		      <mirrorOf>central</mirrorOf>    
		      <name>Human Readable Name for this Mirror.</name>    
		      <url>http://repo2.maven.org/maven2/</url>    
		</mirror>
		<mirror>    
		      <id>ui</id>    
		      <mirrorOf>central</mirrorOf>    
		      <name>Human Readable Name for this Mirror.</name>    
		     <url>http://uk.maven.org/maven2/</url>    
		</mirror>    
		```

---

#### 分布式处理

* 参考文章  
[https://www.zhihu.com/question/22764869](https://www.zhihu.com/question/22764869)

* 目前接触过的  

	* web服务器 - 负载均衡  
	* 关系型数据库（mysql） -  分库分表
	* 关系型数据库（mysql） -  master/slave
	* 本地缓存 guawa cache
	* 分布缓存 memCached / kyotoTycoon
	* 分布式文件 -  nfs / hadoop
	* 系统间结合rabbitmq / kafka
	* 数据处理 hadoop / spark / storm 

---

#### 资源文件

* java.util.ResourceBundle  

	Resource.getBundle方法是如何找到资源文件的呢？  
	> First, it attempts to load a class using the generated class name. If such a class can be found and loaded using the specified class loader, is assignment compatible with ResourceBundle, is accessible from ResourceBundle, and can be instantiated, getBundle creates a new instance of this class and uses it as the result resource bundle.

	首先，根据getBundle传来的路径，找类。找到就实例化
	
	>Otherwise, getBundle attempts to locate a property resource file using the generated properties file name. It generates a path name from the candidate bundle name by replacing all "." characters with "/" and appending the string ".properties". It attempts to find a "resource" with this name using ClassLoader.getResource. (Note that a "resource" in the sense of getResource has nothing to do with the contents of a resource bundle, it is just a container of data, such as a file.) If it finds a "resource", it attempts to create a new PropertyResourceBundle instance from its contents. If successful, this instance becomes the result resource bundle.
	
	如果上一步找不到，就把.都替换成/，类名字+.properties后缀，变成文件路径  
	找文件，这时实例的话类就是PropertiesResourceBundle了。  
	properties文件应该是编译过的ISOxxx编码的文件。（eclipse自动能做）

* java.util.Properties

	* load  
	* loadFromXml

* 辅助类

	* 取得InputStream  
	Class.class.getResourceAsStream(path)  
	path如果是以'/'开头的，那么从classpath根路径开始找   
	如果不是'/'开头的，相对目录

	* servlet取得InputStream  
	context.getResourceAsStream

* spring的MessageResource

	* 几个子类  

		StaticMessageSource - 很少使用  
		ResourceBundleMessageSource  - 常用  
		ReloadableResourceBundleMessageSource - 支持reload

	* 基本用法

		> String getMessage(String code, Object[] args, String default, Locale loc)  
		> String getMessage(String code, Object[] args, Locale loc)
		
		第二个不带默认消息，找不到抛异常
	
		> Spring’s various MessageResource implementations follow the same locale resolution and fallback rules as the standard JDK ResourceBundle
		
		MessageResource基本遵循了java.util.ResourceBundle的规则（properties文件命名等）  

		> As an alternative to ResourceBundleMessageSource, Spring provides a ReloadableResourceBundleMessageSource class. This variant supports the same bundle file format but is more flexible than the standard JDK based ResourceBundleMessageSource implementation. In particular, it allows for reading files from any Spring resource location (not just from the classpath) and supports hot reloading of bundle property files (while efficiently caching them in between).
		
		ReloadableResourceBundleMessageSource,支持reload，可以从任何地方读Message文件。

		> Remember that all ApplicationContext implementations are also MessageSource implementations and so can be cast to the MessageSource interface.
		
		ApplicationContext实现了MessageResource接口，可以直接当MessageResource使用。

---

#### byte

* java中的byte类型就代表一个byte， 8bit

* 一个byte中存储的内容为 0101010101... 的二进制（binary）类型

* 可以使用Byte.parseByte方法将数值型转换为 byte对象  

 这个parseByte方法是有符号的，也就是说一个byte中能存储的范围是 -128 ~ 128  
 如果需要，如果需要 0 - 256的数据，可以使用 Integer.toHexString(b & 0xff)  
 这个为运算是明确的，但是为运算跟负值之间的转换，没明白。

* 简单的byte转换  

 ```
 byte b = Byte.parseByte("16");
 System.out.println(Integer.toHexString(b));
 ```

* System.out.print输出的byte对象为10进行形式

* String -> byte[]  

 * asc2字符， 1个byte长
 * 汉字， 通常是3个byte长

* Long -> byte[]

 * Long型为固定长度的变量，转换为byte[]也占用固定长度，8个字节
 * 长度为 Long.SIZE / Byte.SIZE = 64(bit) / 8(bit) = 8

* Long -> hex string -> byte[]

 * 这种方法可以缩小存储数据占用的空间

 * 原理是 Long为2进制，hex为16进制， 缩短了串的长度  
 然后，16进制最大数为ff，可以2位保存在一个byte中，又节省了空间。
 
 * apache的jar包能提供这样的功能  
 ```
    String s = "1476450601";
    byte[] byteArr = Hex.decodeHex(s.toCharArray());
    char[] charArr = Hex.encodeHex(byteArr3);
    for (char c : charArr) {
        System.out.println(c);
    }
 ```
 
 * 或者javax下的方法
 ```
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
 ```



