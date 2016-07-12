package person.zhao.pool;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;


public class MyPoolObjectFactory extends BasePooledObjectFactory<Person>{
    
    /**
     * 初始化时候调用
     * 其他原因引起pool中对象减少时，调用。
     */
    @Override
    public Person create() throws Exception {
        System.out.println("--  create one object --");
        String age = String.valueOf((int)(Math.random() * 100));
        String name = "name" + age;
        int ageInt = Integer.parseInt(age);
        
        return new Person(name ,ageInt);
    }

    @Override
    public PooledObject<Person> wrap(Person p) {
        return new DefaultPooledObject<Person>(p);
    }
    
    @Override
    public boolean validateObject(PooledObject<Person> p) {

        // 模拟对象不可用的状态（如，链接断开等）
        // 每到 4 结尾的秒数时候，对象有50%几率变成invalidate状态
        String sec = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
        boolean randomFlg = ((int)(Math.random() * 2)) == 0 ? true : false;
        Pattern pattern = Pattern.compile("4$");
        if (pattern.matcher(sec).find() && randomFlg) {
            System.out.println("-- invalid one object, destroy --");
            try {
                destroyObject(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
    }
}
