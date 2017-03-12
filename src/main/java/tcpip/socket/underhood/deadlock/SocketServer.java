package tcpip.socket.underhood.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Chen Li on 2017/3/5.
 */
public class SocketServer {
  public static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
  public static final int PORT = 8081;

  public static void main(String[] args) throws IOException, InterruptedException {
    ServerSocket serverSocket = new ServerSocket(PORT);
    Socket clientSocket = serverSocket.accept();
    InputStream in = clientSocket.getInputStream();
    System.out.println(in.read());
    System.out.println("getReceiveBufferSize " + clientSocket.getReceiveBufferSize());

    while (true) {
      Thread.sleep(12000);
    }
  }
}
