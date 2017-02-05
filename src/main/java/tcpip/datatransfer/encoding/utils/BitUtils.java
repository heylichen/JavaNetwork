package tcpip.datatransfer.encoding.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static tcpip.datatransfer.framing.impl.LengthFramer.BYTEMASK;

/**
 * Created by Chen Li on 2017/1/31.
 */
public class BitUtils {

  public static byte[] getBytes(long value){
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    try {
      out.writeLong(value);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buf.toByteArray();
  }

  public static int encode(byte[] buffer, int offset, long word, int length) {
    for (int i = length - 1; i >= 0; i--) {
      int move = i * 8;
      byte by = (byte) ((word >> move) & BYTEMASK);
      buffer[offset++] = by;
    }
    return offset;
  }

  public static long decode(byte[] buffer, int offset, int length) {
    int endIndex = offset + length;
    long value = 0;
    for (int i = offset; i < endIndex; i++) {
      value = value << 8;
      byte b = buffer[i];
      int inc = b & BYTEMASK;
      value += inc;
    }
    return value;
  }

  public static String toDecimalString(byte[] data) {
    StringBuilder sb = new StringBuilder();
    for (byte b : data) {
      int a = b & BYTEMASK;
      sb.append(String.format("%3d ", a));
    }
    return sb.toString().substring(0, sb.length() - 1);
  }

  public static String toHexString(byte[] data) {
    StringBuilder sb = new StringBuilder();
    for (byte b : data) {
      sb.append(String.format("%02X ", b));
    }
    return sb.toString().substring(0, sb.length() - 1);
  }

  public static String toBitString(byte[] data) {
    StringBuilder sb = new StringBuilder();
    for (byte b : data) {
      sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(" ");
    }
    return sb.toString().substring(0, sb.length() - 1);
  }

  public static void main(String[] args) {

  }
}
