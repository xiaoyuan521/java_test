package person.zhao.pattern.bean;

public class AmericanPerson implements IPerson {
    @Override
    public void talk() {
        System.out.println("hello ~, I'm from the U.S.A");
    }
}
