package tcpip.socket.underhood.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Chen Li on 2017/3/5.
 */
public class SocketClient {
  public static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("127.0.0.1", SocketServer.PORT));
    OutputStream out = socket.getOutputStream();
    System.out.println("SendBufferSize " + socket.getSendBufferSize());
    int count = Integer.MAX_VALUE;

    for (int i = 0; i < count; i++) {
      out.write(1);
      LOGGER.info("{} bytes", i + 1);
    }
    LOGGER.info("send over!");
  }
}
