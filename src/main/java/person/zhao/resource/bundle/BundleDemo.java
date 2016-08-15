package person.zhao.resource.bundle;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * java.util.Bundle
 * 可以处理多国语言的properties文件
 * 从classpath上找这个properties文件。
 */
public class BundleDemo {

    public void p(){
        
        // MyBundle文件需要在classpath上
        // 路径是class的带包名字全路径
        final String  BUNDLE_PATH = "res.bundle.MyBundle";

        // 不指定locale， 采用默认
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PATH);
        String name = bundle.getString("name");
        System.out.println(name);
        
        // 中文locale
        Locale localZh = Locale.CHINESE;
        ResourceBundle bundleZh = ResourceBundle.getBundle(BUNDLE_PATH, localZh);
        name = bundleZh.getString("name");
        System.out.println(name);
        
        // 日语
        Locale localeJa = new Locale("ja", "JP");
        ResourceBundle bundleJa = ResourceBundle.getBundle(BUNDLE_PATH, localeJa);
        name = bundleJa.getString("name");
        System.out.println(name);
    }
    
    public static void main(String[] args){
        new BundleDemo().p();
    }
}
