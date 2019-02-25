package tcpip.socket.underhood.block.detect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcpip.socket.underhood.deadlock.SocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Chen Li on 2017/3/8.
 */
public class SocketChannelClient {
  public static final Logger LOGGER = LoggerFactory.getLogger(SocketChannelClient.class);

  public static void main(String[] args) throws IOException, InterruptedException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);
    socketChannel.connect(new InetSocketAddress("127.0.0.1", SocketServer.PORT));
    ByteBuffer buf = ByteBuffer.wrap("1".getBytes());
    int total = 0;
    while (true) {
      if(!socketChannel.finishConnect()){
        Thread.sleep(100);
        continue;
      }
      int a = socketChannel.write(buf);
      total += a;
      LOGGER.info("{}/{}", a, total);
      buf.rewind();

      if (a == 0) {
        Thread.sleep(500);
      }
    }
  }
}
