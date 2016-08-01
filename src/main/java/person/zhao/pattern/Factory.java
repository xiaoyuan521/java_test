package person.zhao.pattern;

import person.zhao.pattern.bean.AmericanPerson;
import person.zhao.pattern.bean.ChinesePerson;
import person.zhao.pattern.bean.IPerson;
import person.zhao.pattern.bean.JapanesePerson;

/**
 * 简单工厂实例
 * 
 * PersonFactory 生产3个国家的人
 *
 */
public class Factory {
    
    public void p(){
        PersonFactory f = new PersonFactory();
        
        IPerson p = f.create("ZH");
        p.talk();
        
        IPerson p2 = f.create("JP");
        p2.talk();
    }
    
    public static void main(String[] args){
        new Factory().p();
    }
}

class PersonFactory{
    public IPerson create(String type){
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

