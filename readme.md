
## 目录

* [设计模式](https://github.com/yisuren/java_test#设计模式)
* [junit](https://github.com/yisuren/java_test#junit)
* [Apache common Pool2](https://github.com/yisuren/java_test#apache-common-pool2)
* [Thread pool Executor](https://github.com/yisuren/java_test#thread-pool-executor)
* [thread](https://github.com/yisuren/java_test#thread)
* [java local cache](https://github.com/yisuren/java_test#java-local-cache)
* [maven](https://github.com/yisuren/java_test#maven)
* [分布式处理](https://github.com/yisuren/java_test#分布式处理)
* [java资源文件](https://github.com/yisuren/java_test#资源文件)

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

---

#### Thread

* 3种阻塞状态（blocking）
	* 等待阻塞：运行的线程执行wait()方法，JVM会把该线程放入等待池中。(wait会释放持有的锁)
	* 同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。
	* 其他阻塞：运行的线程执行sleep()或join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。（注意,sleep是不会释放持有的锁）

* 文章

	* [http://www.mamicode.com/info-detail-517008.html](http://www.mamicode.com/info-detail-517008.html)

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
		
			> Spring’s various MessageResource implementations follow the same locale resolution and fallback rules as the standard JDK ResourceBundle
			MessageResource基本遵循了java.util.ResourceBundle的规则（properties文件命名等）  

			> As an alternative to ResourceBundleMessageSource, Spring provides a ReloadableResourceBundleMessageSource class. This variant supports the same bundle file format but is more flexible than the standard JDK based ResourceBundleMessageSource implementation. In particular, it allows for reading files from any Spring resource location (not just from the classpath) and supports hot reloading of bundle property files (while efficiently caching them in between).
			ReloadableResourceBundleMessageSource,支持reload，可以从任何地方读Message文件。

			> Remember that all ApplicationContext implementations are also MessageSource implementations and so can be cast to the MessageSource interface.
			ApplicationContext实现了MessageResource接口，可以直接当MessageResource使用。
