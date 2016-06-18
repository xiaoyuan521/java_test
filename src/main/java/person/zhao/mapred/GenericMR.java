package person.zhao.mapred;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class GenericMR {

    private InputStream in = System.in;
    private int keyIndex = 0;

    public GenericMR() {
    }

    public GenericMR(InputStream in) {
        this.in = in;
    }

    public GenericMR(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public GenericMR(InputStream in, int keyIndex) {
        this.in = in;
        this.keyIndex = keyIndex;
    }

    public void reduce(IReducer reducer) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.in));
            RecordReader rr = new RecordReader(br, new TabSpliter());
            while (rr.hasNext()) {
                String keyValue = rr.peak()[keyIndex];
                KeyRecordIterator keyIte = new KeyRecordIterator(rr, keyIndex);
                reducer.reduce(keyValue, keyIte);
            }
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}

class KeyRecordIterator implements Iterator<String[]> {
    private RecordReader rr = null;
    private int keyIndex = 0;
    private String keyValue = null;

    public KeyRecordIterator(RecordReader rr, int keyIndex) {
        this.rr = rr;
        this.keyIndex = keyIndex;

        String[] line = peak();
        this.keyValue = line[keyIndex];
    }

    public boolean hasNext() {
        String[] currentLine = rr.peak();
        if (currentLine == null) {
            return false;
        }
        String currentValue = currentLine[keyIndex];
        if (!keyValue.equals(currentValue)) {
            return false;
        }
        return true;
    }

    public String[] next() {
        String[] line = rr.peak();
        rr.next();
        return line;
    }

    public String[] peak() {
        return rr.peak();
    }

    public void remove() {
        throw new RuntimeException("remove is unsupport method !");
    }

}

class RecordReader {
    private BufferedReader br = null;
    private String[] currentLine = null;
    private String[] nextLine = null;
    private ISpliter spliter = null;

    public RecordReader(BufferedReader br, ISpliter spliter) {

        this.br = br;
        this.spliter = spliter;

        try {
            this.currentLine = readNext();
            this.nextLine = readNext();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public boolean hasNext() {
        return this.currentLine != null;
    }

    public String[] next() {
        try {
            this.currentLine = this.nextLine;
            this.nextLine = readNext();
            return this.currentLine;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String[] peak() {
        return this.currentLine;
    }

    public String[] getNext() {
        return this.nextLine;
    }

    private String[] readNext() throws IOException {
        String line = br.readLine();
        if (line == null) {
            return null;
        } else if ("".equals(line)) {
            return readNext();
        }
        return this.spliter.split(line);
    }
}

interface ISpliter {
    public String[] split(String line);
}

class TabSpliter implements ISpliter {
    private String seperatorStr = "\t";

    public TabSpliter() {
    }

    public String[] split(String line) {
        if (line == null) {
            return null;
        }
        return line.split(this.seperatorStr);
    }
}
