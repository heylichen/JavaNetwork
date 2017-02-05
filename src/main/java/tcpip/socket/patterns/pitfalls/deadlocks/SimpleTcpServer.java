package tcpip.socket.patterns.pitfalls.deadlocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lichen2 on 2017/2/3.
 */
public class SimpleTcpServer {

  public static String SERVER_IP = "127.0.0.1";
  public static int SERVER_PORT = 8081;
  private static int BUF_SIZE = 1024;

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
    byte[] buffer = new byte[BUF_SIZE];
    while (true) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
      InputStream in = clientSocket.getInputStream();
      OutputStream out = clientSocket.getOutputStream();

      int read = 0;
      int offset = 0;
      int leftBufferSize = BUF_SIZE;
      while ((read = in.read(buffer, offset, leftBufferSize)) != -1) {
        offset += read;
        leftBufferSize -= read;
        System.out.println(in.available() + ", read:" + offset);
      }
      out.write(buffer, 0, offset);


      System.out.println("accept from " + clientSocket.getInetAddress().getHostAddress() + ", local:"
          + clientSocket.getLocalSocketAddress() + ", remote:" + clientSocket.getRemoteSocketAddress());
      clientSocket.close();
    }
  }
}
