package person.zhao.anno.mapbean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import person.zhao.anno.mapbean.KeyAnno.Pattern;

public class Base {

    public Person mapToPerson(Map<String, String> personMap) throws Exception {
        Person p = new Person();

        Annotation fieldAnno = null;
        Class<?> fieldType = null;

        Field[] fields = Person.class.getDeclaredFields();
        for (Field f : fields) {
            fieldType = f.getType();
            Annotation[] filedAnnoArr = f.getAnnotations();
            if (filedAnnoArr != null && filedAnnoArr.length > 0) {
                fieldAnno = filedAnnoArr[0];
            }
            if (fieldAnno instanceof KeyAnno) {

                String mapKey = ((KeyAnno)fieldAnno).value();
                Pattern pattern = ((KeyAnno)fieldAnno).pattern();
                String value = personMap.get(mapKey);

                String fieldName = f.getName();
                String methodName = "set" + StringUtils.capitalize(fieldName);
                Method m = p.getClass().getDeclaredMethod(methodName, fieldType);
                m.invoke(p, getTypeValue(value, fieldType, pattern));
            }
        }
        return p;
    }

    private Object getTypeValue(String value, Class<?> c, Pattern pattern) {
        if (c == String.class) {
            return value;
        }
        if (c == int.class || c == Integer.class) {
            return Integer.parseInt(value);
        }
        if (c == Date.class) {
            Date retDate = null;
            try {
                retDate = DateUtils.parseDate(value, pattern.getName());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return retDate;
        }
        return value;
    }

    public void p() throws Exception {
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
        personMap.put("birthday", "1983-03-08");
        personList.add(personMap);

        personMap = new HashMap<String, String>();
        personMap.put("name", "xu_guojin");
        personMap.put("age", "32");
        personMap.put("gender", "male");
        personMap.put("birthday", "1984-05-05");
        personList.add(personMap);

        personMap = new HashMap<String, String>();
        personMap.put("age", "34");
        personMap.put("name", "wen_huan");
        personMap.put("gender", "female");
        personMap.put("birthday", "1982-04-16");
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
