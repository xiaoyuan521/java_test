package person.zhao.resource.bundle;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesDemo {

    // 路径 '/' 开头，是从classpath的根开始找
    InputStream inXml = PropertiesDemo.class.getResourceAsStream("/res/bundle/MyXMLProperties.xml");
    InputStream in = PropertiesDemo.class.getResourceAsStream("/res/bundle/MyProperties.properties");

    public void p() throws Exception {
        Properties p = new Properties();
        p.loadFromXML(inXml);
        String name = p.getProperty("name");
        System.out.println(name);
    }

    public void p2() throws Exception {
        Properties p = new Properties();
        p.load(in);
        String name = p.getProperty("age");
        System.out.println(name);
    }

    public static void main(String[] args) {
        PropertiesDemo demo = new PropertiesDemo();
        try {
            demo.p();
            demo.p2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
