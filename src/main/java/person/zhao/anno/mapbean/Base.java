package person.zhao.anno.mapbean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Base {

    public Person mapToPerson(Map<String, String> personMap) throws Exception {
        Person p = new Person();

        Field[] fields = Person.class.getDeclaredFields();
        for (Field f : fields) {
            Annotation a = null;
            Annotation[] annoArr = f.getAnnotations();
            if (annoArr != null && annoArr.length > 0) {
                a = annoArr[0];
            }
            if (a instanceof KeyAnno) {
                String key = ((KeyAnno)a).value();
                String methodName = "set" + StringUtils.capitalize(key);
                Method m = p.getClass().getDeclaredMethod(methodName, String.class);
                m.invoke(p, personMap.get(key));
            }
        }
        return p;
    }

    public void p() throws Exception {
        // System.out.println(new String("123").getClass() == String.class);
        // if(new String("123") instanceof String){
        // System.out.println("ok ~~~~");
        // }else {
        // System.out.println("ng ~~~~");
        // }

        List<Map<String, String>> pMap = getPersonMap();
        List<Person> pList = new ArrayList<Person>();
        for (Map<String, String> m : pMap) {
            pList.add(mapToPerson(m));
        }
        for (Person p : pList) {
            System.out.println(p.toString());
        }
    }

    public List<Map<String, String>> getPersonMap() {
        List<Map<String, String>> personList = new ArrayList<Map<String, String>>();

        Map<String, String> personMap = new HashMap<String, String>();
        personMap.put("age", "33");
        personMap.put("gender", "male");
        personMap.put("name", "zhaohs");
        personList.add(personMap);

        personMap = new HashMap<String, String>();
        personMap.put("name", "xu_guojin");
        personMap.put("age", "32");
        personMap.put("gender", "male");
        personList.add(personMap);

        personMap = new HashMap<String, String>();
        personMap.put("age", "34");
        personMap.put("name", "wen_huan");
        personMap.put("gender", "female");
        personList.add(personMap);

        return personList;
    }

    public static void main(String[] args) {
        try {
            new Base().p();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
