package tcpip.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class EchoSelectorProtocol implements TCPProtocol {
  private static Logger LOGGER = LoggerFactory.getLogger(EchoSelectorProtocol.class);
  private int bufSize; // Size of I/O buffer

  public EchoSelectorProtocol(int bufSize) {
    this.bufSize = bufSize;
  }

  public void handleAccept(SelectionKey key) throws IOException {
    SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
    clntChan.configureBlocking(false); // Must be nonblocking to register
    // Register the selector with new channel for read and attach byte buffer5.3 Selectors 119
    clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    LOGGER.info("register read for {} ", clntChan.getRemoteAddress());
  }

  public void handleRead(SelectionKey key) throws IOException {
    // Client socket channel has pending data
    SocketChannel clntChan = (SocketChannel) key.channel();
    ByteBuffer buf = (ByteBuffer) key.attachment();
    long bytesRead = clntChan.read(buf);
    if (bytesRead == -1) { // Did the other end close?
      LOGGER.info("close channel for {} ", clntChan.getRemoteAddress());
      clntChan.close();

    } else if (bytesRead > 0) {
      // Indicate via key that reading/writing are both of interest now.
      key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
      ByteBuffer dupBuf = buf.duplicate();
      dupBuf.flip();
      String content = Charset.forName("UTF-8").decode(dupBuf).toString();


      LOGGER.info("read content:[{}] from {}, content length:{} ", content, clntChan.getRemoteAddress(),content.length());
    }
  }

  public void handleWrite(SelectionKey key) throws IOException {
  /*
  * Channel is available for writing, and key is valid (i.e., client channel
  * not closed).
  */
    // Retrieve data read earlier
    ByteBuffer buf = (ByteBuffer) key.attachment();
    buf.flip(); // Prepare buffer for writing
    SocketChannel clntChan = (SocketChannel) key.channel();
    clntChan.write(buf);
    if (!buf.hasRemaining()) { // Buffer completely written?
      // Nothing left, so no longer interested in writes
      key.interestOps(SelectionKey.OP_READ);
    }
    buf.compact(); // Make room for more data to be read in
  }

}