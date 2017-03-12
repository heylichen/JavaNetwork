package tcpip.nio.select;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Created by Chen Li on 2017/2/21.
 */
public class SimpleNioClient {
  private static Logger LOGGER = LoggerFactory.getLogger(SimpleServer.class);

  public static void main(String[] args) {

    SocketChannel serverChannel = null;
    try {
      serverChannel = SocketChannel.open();
      serverChannel.socket().connect(new InetSocketAddress("127.0.0.1", SimpleServer.PORT), 3000);
      serverChannel.configureBlocking(false);
    } catch (IOException e) {
      LOGGER.error("open serverChannel error:{}", Throwables.getStackTraceAsString(e));
      return;
    }

    ByteBuffer writeBuf = ByteBuffer.wrap("hello,world".getBytes(StandardCharsets.UTF_8));
    try {
      while (writeBuf.hasRemaining()) {
        serverChannel.write(writeBuf);
      }
      serverChannel.shutdownOutput();
    } catch (IOException e) {
      LOGGER.error("write error:{}", Throwables.getStackTraceAsString(e));
    } finally {
      try {
        serverChannel.close();
      } catch (IOException e) {
        LOGGER.error("close channel error:{}", Throwables.getStackTraceAsString(e));
      }
    }
  }
}
