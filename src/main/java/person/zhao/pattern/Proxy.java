package person.zhao.pattern;

import person.zhao.pattern.bean.IReadable;
import person.zhao.pattern.bean.Newspager;

/**
 * 装饰器模式，代理模式，模板模式
 * 还有我们常用的匿名内部类实现回调
 * 这几个模式都比较相近，实现上差不多。
 * 可能只是在在对象的关系上略有不同吧。
 * 
 * 这里只用匿名内部类方式来实现一下。
 * 
 */
public class Proxy {

    public void run(IReader reader) {
        System.out.println("-- before read: prepare the newspaper");
        IReadable readable = new Newspager();
        reader.read(readable);
        System.out.println("-- after read: clean up");
    }

    public static void main(String[] args) {
        new Proxy().run(new IReader() {
            @Override
            public void read(IReadable readable) {
                System.out.println(readable.getContent());
            }
        });
    }

}

interface IReader {
    public void read(IReadable readable);
}
