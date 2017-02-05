package tcpip.datatransfer.encoding.utils;

/**
 * Created by Chen Li on 2017/1/31.
 */
public class BitMapOps {
  public static long setBit(long value, int bit) {

    return value | (1L << bit);
  }

  public static long clearBit(long value, int bit) {
    return value & ~(1L << bit);
  }

  public static boolean testBit(long value, int bit) {
    return (value & (1L << bit)) != 0;
  }

  public static void printLongAsBits(long v) {
    System.out.println(BitUtils.toBitString(BitUtils.getBytes(v)));
  }

  public static void main(String[] args) {
    long v = 1L;
    printLongAsBits(v);
    System.out.println(testBit(v, 32));

    v = setBit(1L, 32);
    printLongAsBits(v);
    System.out.println(testBit(v, 32));

    v = clearBit(v, 32);
    printLongAsBits(v);
    System.out.println(testBit(v, 32));
  }
}
