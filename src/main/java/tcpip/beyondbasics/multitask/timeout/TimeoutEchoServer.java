package tcpip.beyondbasics.multitask.timeout;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Chen Li on 2017/2/7.
 */
public class TimeoutEchoServer {
  public static int PORT = 8081;
  private static int TIMEOUT = 10000;
  private static int CLIENT_TIMEOUT = 3000;
  private static int BUFFER_CAPACITY = 1024;
  private static Logger LOGGER = LoggerFactory.getLogger(TimeoutEchoServer.class);


  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(PORT);
      //set timeout for accept, reading
      serverSocket.setSoTimeout(TIMEOUT);
    } catch (IOException e) {
      LOGGER.error("creating server socket bound to port {}, soTimeout={}, stack={}", PORT, TIMEOUT,
          Throwables.getStackTraceAsString(e));
    }

    while (true) {
      Socket clientSocket = null;
      try {
        LOGGER.info("waiting to accept");
        try {
          clientSocket = serverSocket.accept();
          clientSocket.setSoTimeout(CLIENT_TIMEOUT);
        } catch (SocketTimeoutException exception) {
          LOGGER.warn("accept time out.");
          continue;
        }

        LOGGER.info("accept connection from :{}:{}", clientSocket.getRemoteSocketAddress(), clientSocket.getPort());
        InputStream in = clientSocket.getInputStream();
        OutputStream out = clientSocket.getOutputStream();

        byte[] readBuf = new byte[BUFFER_CAPACITY];
        int read = 0;
        while ((read = in.read(readBuf, 0, BUFFER_CAPACITY)) != -1) {
          //no way to timeout write here
          out.write(readBuf, 0, read);
        }
        LOGGER.info("done connection from :{}:{}", clientSocket.getRemoteSocketAddress(), clientSocket.getPort());
      } catch (IOException e) {
        LOGGER.error("error, stack={}",
            Throwables.getStackTraceAsString(e));
      } finally {
        if (clientSocket != null) {
          try {
            clientSocket.close();
          } catch (IOException e) {
            LOGGER.error("close clientSocket error, stack={}",
                Throwables.getStackTraceAsString(e));
          }
        }
      }
    }
  }
}
