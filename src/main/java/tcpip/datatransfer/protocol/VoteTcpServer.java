package tcpip.datatransfer.protocol;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
@Slf4j
public class VoteTcpServer {

    private VoteCoder coder = new BinaryVoteCoder();
    private VoteService voteService = new VoteService();

    public void service() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 11301));

        boolean keep = true;
        while (keep) {
            try (Socket socket = serverSocket.accept();) {
                serviceSocket(socket);
            }
        }
    }

    private void serviceSocket(Socket socket) throws IOException {
        Framer framer = new LengthFramer(socket.getInputStream());

        byte[] msgBytes;
        while ((msgBytes = framer.nextMsg()) != null) {

            VoteRequest req = coder.requestFromWire(msgBytes);
            VoteResponse resp = voteService.handleRequest(req);
            framer.frameMsg(coder.responseToWire(resp), socket.getOutputStream());
        }
    }
}
