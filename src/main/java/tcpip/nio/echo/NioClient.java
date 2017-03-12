package tcpip.nio.echo;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by Chen Li on 2017/2/21.
 */
public class NioClient {
  private static Logger LOGGER = LoggerFactory.getLogger(NioClient.class);

  public static void main(String[] args) {

    SocketChannel channel = null;
    try {
      channel = SocketChannel.open();
      channel.socket().connect(new InetSocketAddress("127.0.0.1", NioEchoServer.PORT), 3000);
      channel.configureBlocking(false);
    } catch (IOException e) {
      LOGGER.error("open serverChannel error:{}", Throwables.getStackTraceAsString(e));
      return;
    }

    String msg = "hello,world";
    byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
    ByteBuffer writeBuf = ByteBuffer.wrap(msgBytes);
    ByteBuffer readBuf = ByteBuffer.allocate(1024);
    try {
      int readBytes = 0;
      int readOnce = 0;
      while (readBytes < msgBytes.length) {
        if (writeBuf.hasRemaining()) {
          channel.write(writeBuf);
        } else if (channel.isOpen()) {
          channel.shutdownOutput();
        }
        readOnce = channel.read(readBuf);
        if (readOnce == -1) {
          throw new IllegalStateException("Socket channel closed prematurely.");
        }
        readBytes += readOnce;
        LOGGER.info("read {} bytes", readBytes);
      }
      String receivedMsg = Charset.forName("UTF-8").decode(readBuf).toString();
      LOGGER.info("read completed, msg:{}", receivedMsg);
    } catch (IOException e) {
      LOGGER.error("write error:{}", Throwables.getStackTraceAsString(e));
    } finally {
      try {
        channel.close();
      } catch (IOException e) {
        LOGGER.error("close channel error:{}", Throwables.getStackTraceAsString(e));
      }
    }
  }
}
