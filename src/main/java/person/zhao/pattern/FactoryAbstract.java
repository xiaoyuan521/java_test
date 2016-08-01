package person.zhao.pattern;

import person.zhao.pattern.bean.AmericanPerson;
import person.zhao.pattern.bean.ChinesePerson;
import person.zhao.pattern.bean.IPerson;
import person.zhao.pattern.bean.JapanesePerson;

/**
 * 抽象工厂
 * 工厂也变成基于接口的了
 * 
 * PersonFactory 生产3个国家的人
 * 每个国家的人，都有专门的工厂来生产
 * 
 * 如果新增加一个国家的人的话，
 * 需要另外增加一个工厂实现，一个人的实现
 * 
 * 工厂方法并不能完全实现开闭原则，
 * 也就是说增加一个新国家的人，还是必须修正代码的。
 * 如果采用Class.forName,或者反射等来实现，那就不属于设计模式的讨论范围了。
 *
 */
public class FactoryAbstract {
    
    public void p(){
        
        IPersonFactory factory = new ChinesePersonFactory();
        IPerson p = factory.create();
        p.talk();
        
    }
    
    public static void main(String[] args){
        new FactoryAbstract().p();
    }
}

interface IPersonFactory {
    public IPerson create();
}

class AmericanPersonFactory implements IPersonFactory{

    @Override
    public IPerson create() {
        return new AmericanPerson();
    }
    
}

class JapanesePersonFactory implements IPersonFactory{

    @Override
    public IPerson create() {
        return new JapanesePerson();
    }
    
}

class ChinesePersonFactory implements IPersonFactory{

    @Override
    public IPerson create() {
        return new ChinesePerson();
    }
    
}

