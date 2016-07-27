package person.zhao.anno.mapbean;

public class Person {

    @KeyAnno(value = "name")
    private String name = null;

    @KeyAnno(value = "age")
    private String age = null;

    @KeyAnno(value = "gender")
    private String gender = null;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] - [%s] - [%s]", getName(), getAge(),getGender());
    }

}
