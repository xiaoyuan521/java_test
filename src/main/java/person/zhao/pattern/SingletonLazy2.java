package person.zhao.pattern;

/**
 * 懒汉式
 * 
 * 使用synchronized解决线程问题
 * 但是每次调用getInstance都要加锁
 *
 */
public class SingletonLazy2 {
    
    private static SingletonLazy2 instance = null;
    
    private SingletonLazy2(){
    }
    
    public synchronized static SingletonLazy2 getInstance(){
        if(instance == null){
            instance = new SingletonLazy2();
        }
        return instance;
    }
    
    public void doStuff(){
        System.out.println("hey , I'm doing some shit !");
    }
}
