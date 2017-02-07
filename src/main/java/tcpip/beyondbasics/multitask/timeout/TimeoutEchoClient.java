package tcpip.beyondbasics.multitask.timeout;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by Chen Li on 2017/2/7.
 */
public class TimeoutEchoClient {
  private static int TIMEOUT = 3000;
  private static String SERVER_IP = "127.0.0.1";//change to www.google.com in china to experience SocketTimeoutException
  private static Logger LOGGER = LoggerFactory.getLogger(TimeoutEchoServer.class);

  public static void main(String[] args) {
    Socket socket = new Socket();
    try {
      LOGGER.info("start");
      socket.connect(new InetSocketAddress(SERVER_IP, TimeoutEchoServer.PORT), TIMEOUT);
      LOGGER.info("connected");
    } catch (IOException e) {
      LOGGER.error("error connecting to {}:{}, stack={}", SERVER_IP, TimeoutEchoServer.PORT, Throwables.getStackTraceAsString(e));
    }
    OutputStream out = null;
    try {
      InputStream in = socket.getInputStream();
      out = socket.getOutputStream();

      Thread.sleep(8000);//this will cause the client socket on the server side read timeout

      //write msg
      byte[] msgBytes = "hello, world!".getBytes(StandardCharsets.UTF_8);
      out.write(msgBytes);

      //test write timeout



      int totalRead = 0;
      int read = 0;

      byte[] readBytes = new byte[msgBytes.length];
      while (totalRead < msgBytes.length) {
        if ((read = in.read(readBytes, totalRead, msgBytes.length - totalRead)) == -1) {
          throw new RuntimeException("socket closed prematurely");
        }
        totalRead += read;
      }
      LOGGER.info("received msg:{}", new String(readBytes,StandardCharsets.UTF_8));
    } catch (Exception e) {
      LOGGER.error("error stack={}",   Throwables.getStackTraceAsString(e));
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}
