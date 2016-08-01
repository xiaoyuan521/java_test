package person.zhao.pattern;

public class Test {

    public static void main(String[] args) {
        
        SingletonHungry s2 = SingletonHungry.getInstance();
        s2.doStuff();
        
//        SingletonLazy s2 = SingletonLazy.getInstance();
//        s2.doStuff();
        
//        SingletonLazy2 s2 = SingletonLazy2.getInstance();
//        s2.doStuff();
    }
}
