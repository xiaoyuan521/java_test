package person.zhao.pattern.bean;

public class ChinesePerson implements IPerson {

    @Override
    public void talk() {
        System.out.println("你好，我是中国人。");
    }

}
