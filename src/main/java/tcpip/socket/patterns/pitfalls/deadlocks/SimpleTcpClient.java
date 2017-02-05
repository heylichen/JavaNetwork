package tcpip.socket.patterns.pitfalls.deadlocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static tcpip.socket.patterns.pitfalls.deadlocks.SimpleTcpServer.SERVER_IP;
import static tcpip.socket.patterns.pitfalls.deadlocks.SimpleTcpServer.SERVER_PORT;

/**
 * Created by lichen2 on 2017/2/3.
 */
public class SimpleTcpClient {


  public static void main(String[] args) throws IOException {

    Socket socket = new Socket(SERVER_IP, SERVER_PORT);
    OutputStream out = socket.getOutputStream();
    InputStream in = socket.getInputStream();
    //write msg bytes
    String msg = "hello, world!";
    byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
    out.write(msgBytes);
    out.flush();

    byte[] buffer = new byte[1000];
    int read = 0;
    int offset = 0;

    while (offset < msgBytes.length) {
      if ((read = in.read(buffer, offset, buffer.length)) == -1) {
        throw new IllegalStateException("ex");
      }

      offset += read;
    }
    socket.close();

    System.out.println(new String(buffer, 0, offset, StandardCharsets.UTF_8));


  }
}
