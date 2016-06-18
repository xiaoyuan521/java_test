package person.zhao.mapred;

import java.util.Iterator;

public interface IReducer {
    public void reduce(String key, Iterator<String[]> recordIterator);
}