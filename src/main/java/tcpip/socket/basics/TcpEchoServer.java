package tcpip.socket.basics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lichen2 on 2017/1/24.
 */
public class TcpEchoServer {
  private static Logger LOGGER = LoggerFactory.getLogger(TcpEchoServer.class);
  private static final int BUF_SIZE = 128;

  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Parameter(s): <Port>");
    }
    byte[] buffer = new byte[BUF_SIZE];
    int port = Integer.parseInt(args[0]);
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      System.err.println("create ServerSocket error, " + e);
    }
    while (true) {
      try {
        Socket cliSocket = serverSocket.accept();
        LOGGER.info("accept from " + cliSocket.getInetAddress().getHostAddress() + ", local:"
            + cliSocket.getLocalSocketAddress() + ", remote:" + cliSocket.getRemoteSocketAddress());

        InputStream in = cliSocket.getInputStream();
        OutputStream out = cliSocket.getOutputStream();
        int receivedBytes = 0;

        while ((receivedBytes = in.read(buffer, 0, 1)) != -1) {
          out.write(buffer, 0, receivedBytes);
        }
        cliSocket.close();
      } catch (Exception e) {
        System.err.println("accept and processing msg error, " + e);
      }
    }
  }
}
