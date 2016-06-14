package person.zhao.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

public class GenericMR {

    private InputStream in = System.in;
    private String splitStr = "\\t";
    private int keyIndex = 0;

    public GenericMR() {
    }

    public GenericMR(InputStream in) {
        this.in = in;
    }

    public GenericMR(String splitStr) {
        this.splitStr = splitStr;
    }
    
    public GenericMR(int keyIndex) {
        this.keyIndex = keyIndex;
    }
    
    public GenericMR(InputStream in, String splitStr, int keyIndex) {
        this.in = in;
        this.splitStr = splitStr;
        this.keyIndex = keyIndex;
    }

    private void reduce(IReducer reducer) throws IOException {
        BufferedReader bin = new BufferedReader(new InputStreamReader(this.in));

        String line = "";
        while ((line = bin.readLine()) != null) {
        }

        bin.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            InputStream in = GenericMR.class.getClassLoader().getResourceAsStream("a.tsv");
            new GenericMR(in).reduce(new IReducer() {

                public void reduce(String key, Iterator<String[]> keyRecordIterator) {
                       System.out.println(String.format("==== key is [%s] ====", key));
                       String[] recordArr = null;
                       while(keyRecordIterator.hasNext()){
                           recordArr = keyRecordIterator.next();
                           System.out.println(StringUtils.join(recordArr));
                       }
                }
            });
        } catch (IOException e) {
            System.out.println("some thing is wrong with the IO");
            e.printStackTrace();
        }
    }
}

class KeyRecordIterator{

    
}

class RecordReader{
    BufferedReader br = null;
    String currentLine = null;
    String nextLine = null;
    
    public RecordReader(BufferedReader br) {
        this.br = br;
    }
    
    public boolean hasNext(){
        return this.nextLine == null;
    }
    
    public String next(){
        try {
            this.currentLine = br.readLine();
            return this.currentLine;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

interface IReducer {
    public void reduce(String key, Iterator<String[]> recordIterator);
}

interface ISpliter{
    public String[] split(String line, String seperatorStr);
}

class Spliter implements ISpliter{
    public String[] split(String line, String seperatorStr) {
        return line.split(seperatorStr);
    }
}

