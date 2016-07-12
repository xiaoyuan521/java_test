#### junit的运行方法

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

#### Apache common Pool

	* 对象池
	* 常用的是GenericeObjectPool, 创建需要一个Factory， 一个config
	* 使用对象的时候，从pool中borrow，使用完return
	* 异常处理， 看代码示例吧
	* 常用的是GenericeObjectPool对象是线程安全的