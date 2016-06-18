package person.zhao.mapred;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Sum {

    public void doSum() throws IOException{
        InputStream in = Sum.class.getClassLoader().getResourceAsStream("person.tsv");
        new GenericMR(in, 1).reduce(new IReducer() {
            public void reduce(String key, Iterator<String[]> recordIterator) {

                int ageSum = 0;
                int age = 0;
                String[] lineArr = null;
                while(recordIterator.hasNext()){
                    lineArr = recordIterator.next();
                    age = Integer.parseInt(lineArr[2]);
                    ageSum += age;
                }

                System.out.println(String.format("%s\t%s", key, String.valueOf(ageSum)));
            }
        });
    }

    public static void main(String[] args){
        try {
            new Sum().doSum();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
