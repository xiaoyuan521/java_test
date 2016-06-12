package person.zhao.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {
    
    private void p(InputStream in) throws IOException {
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        
        String line = "";
        if((line = bin.readLine()) != null){
            System.out.println(line);
        }
    }

    public static void main(String[] args){
        System.out.println("111");
        
        try {
            new Test().p(System.in);
        } catch (IOException e) {
            System.out.println("some thing is wrong with the IO");
            e.printStackTrace();
        }
    }
}
