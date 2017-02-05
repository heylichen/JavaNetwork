package tcpip.beyondbasics.multitask;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
  private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EchoProtocol.class);
  private static final int BUFSIZE = 32; // Size (in bytes) of I/O buffer
  private Socket clntSock; // Socket connect to client


  public EchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
  }

  public static void handleEchoClient(Socket clntSock, Logger logger) {
    try {
      // Get the input and output I/O streams from socket
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize; // Size of received message
      int totalBytesEchoed = 0; // Bytes received from client
      byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer
      // Receive until client closes connection, indicated by -1
      while ((recvMsgSize = in.read(echoBuffer)) != -1) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
      }

      LOGGER.info("Client " + clntSock.getRemoteSocketAddress() + ", echoed "
          + totalBytesEchoed + " bytes.");

    } catch (IOException ex) {
      logger.log(Level.WARNING, "Exception in echo protocol", ex);
    } finally {
      try {
        clntSock.close();
      } catch (IOException e) {
      }
    }
  }

  public void run() {
    handleEchoClient(clntSock, null);
  }
}