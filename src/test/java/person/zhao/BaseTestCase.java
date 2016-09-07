package person.zhao;

import java.io.File;

import junit.framework.TestCase;

/**
 * 这个类，可以作为左右testcase的基类
 * 可以写一些共通方法
 * 
 * 为了让这个类不被当做test类执行 -> abstract
 */
public abstract class BaseTestCase extends TestCase {

    protected String getResourcePath(String basePath, String folderName) {
        String baseFile = BaseTestCase.class.getClassLoader().getResource(basePath).getFile();
        File retFile = new File(baseFile, folderName);
        return retFile.getPath();
    }

    protected String getResourcePath(String folderName) {
        return getResourcePath("", folderName);
    }
}
