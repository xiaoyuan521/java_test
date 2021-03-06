package person.zhao.pool;

public class Person {
    private String name = null;
    private int age = 0;
    
    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return String.format("my name is [%s], age is [%s]", this.getName(), this.getAge());
    }

}
