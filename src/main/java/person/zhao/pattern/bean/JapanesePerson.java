package person.zhao.pattern.bean;

public class JapanesePerson implements IPerson {
    @Override
    public void talk() {
        System.out.println("こにちは、私は日本人です");
    }
}
