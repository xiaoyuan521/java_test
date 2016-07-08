package person.zhao.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;


public class MyPoolObjectFactory extends BasePooledObjectFactory<Person>{
    
    @Override
    public Person create() throws Exception {
        String age = String.valueOf((int)(Math.random() * 100000));
        String name = "name" + age;
        int ageInt = Integer.parseInt(age);
        
        return new Person(name ,ageInt);
    }

    @Override
    public PooledObject<Person> wrap(Person p) {
        return new DefaultPooledObject<Person>(p);
    }

}
