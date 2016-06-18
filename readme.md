* junit的运行方法

	* junit 4.x
	如果低于4版本，下面有些方法是不支持的  
	例如，执行特定类的特定方法。

	* 使用了surface plugin
	提供junit测试的report  
	提供各种测试方法  
	[https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html](https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html)  

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

