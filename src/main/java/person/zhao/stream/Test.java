package person.zhao.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {

    public void p() throws IOException{
        InputStream in = GenericMR.class.getClassLoader().getResourceAsStream("blank.tsv");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        output(line);
        String line2 = br.readLine();
        output(line2);
        String line3 = br.readLine();
        output(line3);
    }

    private void output(String line) {
        if("".equals(line)){
            System.out.println("blank ...");
        }else if(line == null){
            System.out.println("nnnnnnnn");
        }else {
            System.out.println("line is : " + line);
        }
    }
    
    public static void main(String[] args){
        try {
            new Test().p();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
