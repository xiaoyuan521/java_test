package person.zhao.pattern;

/**
 * 饿汉式
 * 
 * 线程安全
 * 原理： 类加载是线程安全的，并且是JVM负责的
 * 类的静态变量自然也线程安全
 */
public class SingletonHungry {
    
    private final static SingletonHungry INSTANCE = new SingletonHungry();
    
    private SingletonHungry(){
    }
    
    public static SingletonHungry getInstance(){
        return INSTANCE;
    }
  
    public void doStuff(){
        System.out.println("hey , I'm doing some shit !");
    }
}
