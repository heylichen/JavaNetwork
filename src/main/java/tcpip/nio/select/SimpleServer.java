package tcpip.nio.select;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * Created by Chen Li on 2017/2/20.
 */
public class SimpleServer {
  private static Logger LOGGER = LoggerFactory.getLogger(SimpleServer.class);
  public static int PORT = 8081;

  public static void main(String[] args) {
    Selector selector = null;
    try {
      selector = Selector.open();
    } catch (IOException e) {
      LOGGER.error("open selector error:{}", Throwables.getStackTraceAsString(e));
      return;
    }

    ServerSocketChannel serverChannel = null;
    try {
      serverChannel = ServerSocketChannel.open();
      serverChannel.bind(new InetSocketAddress(PORT));
      serverChannel.configureBlocking(false);
    } catch (IOException e) {
      LOGGER.error("open serverChannel error:{}", Throwables.getStackTraceAsString(e));
      return;
    }

    try {
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    } catch (ClosedChannelException e) {
      LOGGER.error("serverChannel.register error:{}", Throwables.getStackTraceAsString(e));
      return;
    }
    SelectionHandler handler = new SelectionHandler();
    ByteBuffer readBuf = ByteBuffer.allocate(1024);

    try {
      int round = 0;
      int keyCount = 0;
      while (true) {
        if ((keyCount=selector.select(1000)) <= 0) {
          LOGGER.info("select for {} ms.", 5000);
          continue;
        }
        round++;
        LOGGER.info("--> selected keys:{}, round:{}, ", keyCount, round);
        Set<SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
          if (key.isValid() && key.isAcceptable()) {
            handler.accept(key);
          }

          if (key.isValid() && key.isReadable()) {
            handler.read(key);
          }
          if (key.isValid() && key.isWritable()) {
            handler.write(key);
          }
          keys.remove(key);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
