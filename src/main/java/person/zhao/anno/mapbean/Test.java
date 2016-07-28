package person.zhao.anno.mapbean;

public class Test {

    public void p() throws Exception {

        System.out.println(new String("123").getClass() == String.class);

        if (new String("123") instanceof String) {
            System.out.println("ok ~~~~");
        } else {
            System.out.println("ng ~~~~");
        }

        Class<?> c = Person.class.getDeclaredField("age").getType();
        System.out.println(c);
        System.out.println(c == int.class);
        System.out.println(c == Integer.class);
        System.out.println(c.equals(int.class));
    }

    public static void main(String[] args) {
        try {
            new Test().p();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
