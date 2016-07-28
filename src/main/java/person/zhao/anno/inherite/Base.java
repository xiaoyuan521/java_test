package person.zhao.anno.inherite;

/**
 * 声明了注解的类是否能被继承呢
 * 
 * 如果注解中使用了@inherited,那么就能继承
 * 如果没使用，不能被继承
 * 
 * 子类能继承父类中带@inherited的注解
 * 实现类不能继承接口中带有@inherited的注解
 *
 */
public class Base {

    public void p(){
        System.out.println(GoodPerson.class.getAnnotations()[0]);
    }
    
    public static void main(String[] args) {
        new Base().p();
    }

}
