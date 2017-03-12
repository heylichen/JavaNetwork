package tcpip.nio.echo;

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
public class EchoSelectionHandler {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public void accept(SelectionKey key) throws IOException {
    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
    SocketChannel clientChannel = serverSocketChannel.accept();
    //ignore IllegalBlockingModeException
    clientChannel.configureBlocking(false);
    ByteBuffer readBuf = ByteBuffer.allocate(1024);
    clientChannel.register(key.selector(), SelectionKey.OP_READ, readBuf);
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

      clientChannel.shutdownInput();
    } else {
      clientChannel.register(key.selector(), SelectionKey.OP_WRITE, tempBuf);
      logger.info("read {} bytes, has more.", bytes);
    }
  }

  public void write(SelectionKey key) throws IOException {
    SocketChannel clientChannel = (SocketChannel) key.channel();
    logger.info("is ready to write, {}", clientChannel.getRemoteAddress());
    ByteBuffer tempBuf = (ByteBuffer) key.attachment();
    tempBuf.flip();

    if (tempBuf.hasRemaining()) {
      logger.info("has remaining.");
      clientChannel.write(tempBuf);
    }

    tempBuf.compact();

  }
}
