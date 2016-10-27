package person.zhao.others;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class ByteDemo {
    
    public void p(){
        // 默认为使用10进制转换
        byte b = Byte.parseByte("16");
        // 默认输出也为10进制，输出16
        System.out.println(b);
        
        // 使用16进制转换， 输出22
        byte b2 = Byte.parseByte("16", 16);
        System.out.println(b2); 
    }
    
    public void p2(){
        // 长度3
        String s = "123";
        System.out.println(s.getBytes().length);
        
        // 长度6
        String s2 = "中国";
        System.out.println(s2.getBytes().length);
    }
    
    /**
     * decodeHex 方法是将一组16进制的string串，
     * 转换为byte数组，
     * 由于16进制2位放到一个byte中，所以转换后的byte[]长度为转换前的一半儿
     * @throws DecoderException
     */
    public void p3() throws DecoderException{
        String s = "1476450601"; 
        byte[] byteArr = Hex.decodeHex(s.toCharArray());
        System.out.println(byteArr.length);
        
        long longValue = 1476450601l; // 58 00  d9  29
        String hexString = Long.toHexString(longValue);
        byte[] byteArr2 = Hex.decodeHex(hexString.toCharArray());
        System.out.println(byteArr2.length);
        
        char[] charArr = Hex.encodeHex(byteArr2);
        System.out.println(new String(charArr));
    }
    
    public static void main(String[] args) {
        try {
            new ByteDemo().p3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
