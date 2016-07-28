package person.zhao.anno.mapbean;

import java.util.Date;

public class Person {

    @KeyAnno(value = "name")
    private String name = null;

    @KeyAnno(value = "age")
    private int age = 0;

    @KeyAnno(value = "gender")
    private String gender = null;

    @KeyAnno(value = "birthday", pattern = KeyAnno.Pattern.SHORT)
    private Date birth = null;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return String.format("[%s] - [%d] - [%s] - [%s]", getName(), getAge(), getGender(), getBirth().toString());
    }

}
