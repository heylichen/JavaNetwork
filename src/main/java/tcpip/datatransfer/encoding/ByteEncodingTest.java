package tcpip.datatransfer.encoding;

import static tcpip.datatransfer.encoding.utils.BitUtils.decode;
import static tcpip.datatransfer.encoding.utils.BitUtils.encode;
import static tcpip.datatransfer.encoding.utils.BitUtils.toDecimalString;

/**
 * Created by Chen Li on 2017/1/30.
 */
public class ByteEncodingTest {
  private final static int BSIZE = Byte.SIZE / Byte.SIZE;
  private final static int SSIZE = Short.SIZE / Byte.SIZE;
  private final static int ISIZE = Integer.SIZE / Byte.SIZE;
  private final static int LSIZE = Long.SIZE / Byte.SIZE;

  private final static int BYTEMASK = 0xFF; // 8 bits


  public static void main(String[] args) {
    simpleWrite2Bytes();
//    byte[] buffer = new byte[10];
//    int v = 0x01FF0A04;
//    int end = encode(buffer, 0, v, ISIZE);
//
//    byte[] bytes = new byte[end];
//    System.arraycopy(buffer, 0, bytes, 0, end);
//
//    System.out.println(toDecimalString(bytes));
//    System.out.println(toHexString(bytes));
//    System.out.println(toBitString(bytes));
  }

  private static void simpleWrite2Bytes() {
    byte byteVal = 101; // one hundred and one
    short shortVal = 10001; // ten thousand and one
    int intVal = 100000001; // one hundred million and one
    long longVal = 1000000000001L;// one trillion and one

    int BSIZE = Byte.SIZE / Byte.SIZE;
    int SSIZE = Short.SIZE / Byte.SIZE;
    int ISIZE = Integer.SIZE / Byte.SIZE;
    int LSIZE = Long.SIZE / Byte.SIZE;

    byte[] dataBytes = new byte[100];
    int byteIndex = 0;
    int shortIndex = 0;
    int intIndex = 0;
    int longIndex = 0;

    shortIndex = encode(dataBytes, 0, byteVal, BSIZE);
    intIndex = encode(dataBytes, shortIndex, shortVal, SSIZE);
    longIndex = encode(dataBytes, intIndex, intVal, ISIZE);
    int currentIndex = encode(dataBytes, longIndex, longVal, LSIZE);
    byte[] collected = new byte[currentIndex];
    System.arraycopy(dataBytes, 0, collected, 0, currentIndex);

    System.out.println(toDecimalString(collected));

    long v = decode(collected, byteIndex, BSIZE);
    System.out.println(v);
    v = decode(collected, shortIndex, SSIZE);
    System.out.println(v);
    v = decode(collected, intIndex, ISIZE);
    System.out.println(v);
    v = decode(collected, longIndex, LSIZE);
    System.out.println(v);
  }
}
