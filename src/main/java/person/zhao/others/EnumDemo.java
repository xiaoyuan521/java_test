package person.zhao.others;

public class EnumDemo {

    public void p() {
        // 字符串输出枚举的名字
        System.out.println(COLORS.BLUE.name());
        // 输出枚举所在的下标位置
        System.out.println(COLORS.BLUE.ordinal());

        // 循环输出
        COLORS[] colorArr = COLORS.values();
        for (COLORS c : colorArr) {
            System.out.println(c.name());
        }
    }
    
    public void p2(){
        PERSON[] personArr = PERSON.values();
        for(PERSON p: personArr){
            System.out.println(p.toString());
            
            // enum类型与swith case的结合使用
            switch (p) {
                case ZHAO:
                    System.out.println("the good one ~");
                    break;
                case XU:
                    System.out.println("the choosen one ~");
                    break;
                case WEN:
                    System.out.println("the beauty one ~");
                    break;
                default:
                    System.out.println("no one ~");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new EnumDemo().p2();
    }
}

/**
 * 普通枚举类
 * @author zhao_hongsheng
 *
 */
enum COLORS {
    RED, YELLOW, BLUE
}

/**
 * 自定义字段和方法的枚举类
 * @author zhao_hongsheng
 *
 */
enum PERSON {
    
    ZHAO("zhaohs", 34), XU("xu_guojin", 33), WEN("wen_huan", 35);
    
    private String name = null;
    private int age = 0;
    
    /**
     * 构造方法，只能是private的
     * @param name
     * @param age
     */
    private PERSON(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * 覆盖父类的方法
     */
    @Override
    public String toString() {
        return String.format("name is [%s], age is [%d]", getName(), getAge());
    }

}
