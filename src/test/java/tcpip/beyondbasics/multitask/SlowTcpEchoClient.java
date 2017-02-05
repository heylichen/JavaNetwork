package tcpip.beyondbasics.multitask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by lichen2 on 2017/1/24.
 */
public class SlowTcpEchoClient {
  private static Logger LOGGER = LoggerFactory.getLogger(SlowTcpEchoClient.class);

  private static final Charset CHARSET = StandardCharsets.UTF_8;

  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      throw new IllegalArgumentException("Parameter(s): ip <Port> msg");
    }

    String ip = args[0];
    int port = Integer.parseInt(args[1]);
    byte[] send = args[2].getBytes(CHARSET);
    byte[] received = new byte[send.length];
    Socket socket = new Socket(ip, port);

    System.out.println("client is bounded:" + socket.isBound()
        + ", " + socket.getLocalSocketAddress() + "," + socket.getRemoteSocketAddress());

    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();
    out.write(send);

    int totalRead = 0;
    int read = 0;
    int round = 0;
    while (totalRead < send.length) {
      if ((read = in.read(received, totalRead, send.length - totalRead)) == -1) {
        throw new SocketException("Connection closed prematurely");
      }
      totalRead += read;
      round++;
    }
    System.out.println("receive:" + new String(received, CHARSET) + ", read " + round + " times.");

    LOGGER.info("sleeping！");
    Thread.sleep(3000);
    LOGGER.info("done!");
    socket.close();
  }
}
