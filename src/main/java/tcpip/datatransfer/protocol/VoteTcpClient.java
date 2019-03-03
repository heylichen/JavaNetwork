package tcpip.datatransfer.protocol;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class VoteTcpClient {
    private VoteCoder coder = new BinaryVoteCoder();

    public void request(int type, int candidateId) throws IOException {
        try (Socket socket = new Socket("localhost", 11301);) {

            Framer framer = new LengthFramer(socket.getInputStream());
            request(framer, socket.getOutputStream(), type, candidateId);
        }
    }

    private void request(Framer framer, OutputStream os, int type, int candidateId) throws IOException {
        VoteRequest req = new VoteRequest(type, candidateId);
        framer.frameMsg(coder.requestToWire(req), os);
        VoteResponse resp = coder.responseFromWire(framer.nextMsg());
        log.info("resp {}", JSON.toJSONString(resp));
    }
}
