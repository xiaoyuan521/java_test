package person.zhao.pattern;

import person.zhao.pattern.bean.IReadable;
import person.zhao.pattern.bean.StoryBook;

/**
 * 模板(template)模式<br> 
 * 读书分3步<br> 
 * 
 * 采用抽象类 + 继承来实现<br>
 * 
 * 1. 取得读物<br> 
 * 2. 读 <br>
 * 3. 还读物 <br> 
 * 用户只关心读的部分，取和还，是不关心部分，可以使用模板来处理
 */
public class Template extends MyTemplate {

    @Override
    protected void read(IReadable readable) {
        System.out.println(readable.getContent());
    }

    public static void main(String[] args) {
        new Template().run();
    }

}

abstract class MyTemplate {
    public void run() {
        // get
        IReadable readable = null;
        readable = getReadable();
        // read
        read(readable);
        // return
        returnReadable();
    }

    protected IReadable getReadable() {
        System.out.println("get the readable");
        return new StoryBook();
    }

    protected abstract void read(IReadable readable);

    protected void returnReadable() {
        System.out.println("return the readable");
    }
}
