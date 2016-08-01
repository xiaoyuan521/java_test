package person.zhao.pattern;

import person.zhao.pattern.bean.AmericanPerson;
import person.zhao.pattern.bean.ChinesePerson;
import person.zhao.pattern.bean.IPerson;
import person.zhao.pattern.bean.JapanesePerson;

/**
 * 静态工厂实例
 * 只是把create方法变为了静态的方法
 * 
 * PersonFactory 生产3个国家的人
 *
 */
public class FactoryStatic {
    
    public void p(){
        IPerson p = PersonFactoryStatic.create("ZH");
        p.talk();
        
        IPerson p2 = PersonFactoryStatic.create("JP");
        p2.talk();
    }
    
    public static void main(String[] args){
        new FactoryStatic().p();
    }
}

class PersonFactoryStatic{
    public static IPerson create(String type){
        if("JP".equals(type)){
            return new JapanesePerson();
        }else if("USA".equals(type)){
            return new AmericanPerson();
        }else if("ZH".equals(type)){
            return new  ChinesePerson();
        }else {
            throw new RuntimeException("not support type");
        }
    }
}

