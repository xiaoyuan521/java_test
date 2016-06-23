package person.zhao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import person.zhao.mapred.GenericMR;
import person.zhao.mapred.IReducer;

public class GenericMRTest extends TestCase {

    private final static String RESULT_NAME = "result.tsv";
    private final static String RESULT_DIR = System.getProperty("java.io.tmpdir");
    private static String RESULT_PATH = null;
    static {
        RESULT_PATH = join(RESULT_DIR, RESULT_NAME);
    }

    @Override
    protected void setUp() throws Exception {
        new File(RESULT_PATH).delete();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        new File(RESULT_PATH).delete();
        super.tearDown();
    }

    public GenericMRTest() {
    }

    /**
     * 正常的case
     *
     * @throws IOException
     */
    public void test1() throws IOException {
        sum(getInputStream("t_1.tsv"));
        checkResultFile();
    }

    /**
     * 空白文件
     *
     * @throws IOException
     */

    public void test2() throws IOException {
        sum(getInputStream("t_2.tsv"));
        checkResultFileBlank();
    }

    /**
     * 空白文件只有空行
     *
     * @throws IOException
     */
    public void test3() throws IOException {
        sum(getInputStream("t_3.tsv"));
        checkResultFileBlank();
    }

    /**
     * 首行空行，末行空行，中间空行
     *
     * @throws IOException
     */
    public void test4() throws IOException {
        sum(getInputStream("t_4.tsv"));
        checkResultFile();
    }

    /**
     * 非法格式，非tsv
     */
    @Test(expected = RuntimeException.class)
    public void test5() {
        try{
        sum(getInputStream("t_5.tsv"));
        fail("should throw exception ...");
        }catch(Exception e){
//            assertEquals(e.getClass().getName(), "java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    /**
     * 非法格式，空行带空格
     */
    public void test6() {
        try{
        sum(getInputStream("t_6.tsv"));
        fail("should throw exception ...");
        }catch(Exception e){
//            assertEquals(e.getClass().getName(), "java.lang.ArrayIndexOutOfBoundsException");
        }
    }
    
    /**
     * 正常case，测试集计列的位置
     *
     * @throws IOException
     */
    public void test7() throws IOException {
        sum(getInputStream("t_7.tsv"), 2, 3);
        checkResultFile();
    }
    
    private void sum(InputStream in) {
        sum(in , 1, 2);
    }

    private void sum(InputStream in, int groupKeyIndex, final int sumIndex) {
        final List<String> lines = new ArrayList<String>();
        new GenericMR(in, groupKeyIndex).reduce(new IReducer() {
            public void reduce(String key, Iterator<String[]> recordIterator) {

                int ageSum = 0;
                int age = 0;
                String[] lineArr = null;
                while (recordIterator.hasNext()) {
                    lineArr = recordIterator.next();
                    age = Integer.parseInt(lineArr[sumIndex]);
                    ageSum += age;
                }

                lines.add(String.format("%s\t%s", key, String.valueOf(ageSum)));
            }
        });
        output(lines);
    }

    private InputStream getInputStream(String tsvName) {
        InputStream in = GenericMRTest.class.getClassLoader().getResourceAsStream(tsvName);
        return in;
    }

    private void output(List<String> lines) {
        PrintWriter fout = null;
        try {
            fout = new PrintWriter(new File(RESULT_PATH));
            for (String line : lines) {
                fout.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            fout.close();
        }

    }

    private void checkResultFile() throws IOException {

        InputStream in = new FileInputStream(RESULT_PATH);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            List<String> resultList = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                resultList.add(line);
            }

            assertEquals(resultList.size(), 2);
            assertEquals(resultList.get(0), "male\t65");
            assertEquals(resultList.get(1), "female\t51");
        } finally {
            br.close();
        }
    }

    private void checkResultFileBlank() throws IOException {
        InputStream in = new FileInputStream(RESULT_PATH);
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(in));
        try {
            List<String> resultList = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                resultList.add(line);
            }

            assertEquals(resultList.size(), 0);
        } finally {
            br.close();
        }
    }

    private static String join(String... paths) {
        File f = null;
        for (String p : paths) {
            if (f == null) {
                f = new File(p);
            } else {
                f = new File(f, p);
            }
        }
        return f.getPath();
    }

}
