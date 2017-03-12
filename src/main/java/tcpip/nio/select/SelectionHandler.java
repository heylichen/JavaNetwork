package tcpip.nio.select;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by Chen Li on 2017/2/21.
 */
public class SelectionHandler {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public void accept(SelectionKey key) throws IOException {
    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
    SocketChannel clientChannel = serverSocketChannel.accept();
    //ignore IllegalBlockingModeException
    clientChannel.configureBlocking(false);
    ByteBuffer readBuf = ByteBuffer.allocate(1024);
    clientChannel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE, readBuf);
    logger.info("accepted from :{}", clientChannel.getRemoteAddress());
  }

  public void read(SelectionKey key) throws IOException {
    SocketChannel clientChannel = (SocketChannel) key.channel();

    logger.info("read ready from {}", clientChannel.getRemoteAddress());
    ByteBuffer tempBuf = (ByteBuffer) key.attachment();
    int bytes = clientChannel.read(tempBuf);
    if (bytes == -1) {
      ByteBuffer readBuf = tempBuf.duplicate();
      readBuf.flip();
      String msg = Charset.forName("UTF-8").decode(readBuf).toString();
      logger.info("read completed, msg:{}", msg);

      clientChannel.close();
    } else {
      //
      logger.info("read {} bytes, has more.", bytes);
    }
  }

  public void write(SelectionKey key) throws IOException {
    SocketChannel clientChannel = (SocketChannel) key.channel();
    logger.info("is ready to write, {}", clientChannel.getRemoteAddress());
  }
}
