package person.zhao.pattern;

/**
 * 懒汉式
 * 原理是内部类只有第一次被调用时才加载
 * 
 * 采用内部类来实现线程安全
 * 内部类的线程安全是JVM实现的。
 *
 */
public class SingletonLazy {
    
    private SingletonLazy(){
    }
    
    public static SingletonLazy getInstance(){
        return InstanceHolder.INSTANCE;
    }
  
    private static class InstanceHolder{
        private final static SingletonLazy INSTANCE = new SingletonLazy(); 
    }
    
    public void doStuff(){
        System.out.println("hey , I'm doing some shit !");
    }
}
