package tcpip.socket.underhood.block.detect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Chen Li on 2017/3/8.
 */
public class ServerSocketChannelSample {
  public static final int PORT = 8081;
  public static final Logger LOGGER = LoggerFactory.getLogger(ServerSocketChannelSample.class);
  public static void main(String[] args) throws IOException {
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    serverChannel.configureBlocking(false);
    serverChannel.socket().bind(new InetSocketAddress(PORT));

    Selector selector = Selector.open();
    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    ByteBuffer received = ByteBuffer.allocate(1);

    while (selector.select() > 0) {
      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        LOGGER.info("selected.");
        if (key.isAcceptable()) {
          ServerSocketChannel selectedServerChannel = (ServerSocketChannel) key.channel();
          SocketChannel socketChannel = selectedServerChannel.accept();
          socketChannel.configureBlocking(false);

          socketChannel.register(selector, SelectionKey.OP_READ, received);
          LOGGER.info("register read");
        }

        if (key.isReadable()) {
          SocketChannel readSocket = (SocketChannel) key.channel();
          ByteBuffer readBuf = (ByteBuffer) key.attachment();
          int readBytes = readSocket.read(readBuf);
          LOGGER.info("read {} bytes", readBytes);
        }

        iterator.remove();
      }
    }
  }



}
