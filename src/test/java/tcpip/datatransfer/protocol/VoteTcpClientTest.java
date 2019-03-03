package tcpip.datatransfer.protocol;

import org.junit.Test;

import java.io.IOException;

public class VoteTcpClientTest {
    @Test
    public void serviceTest() throws IOException {
        VoteTcpServer server = new VoteTcpServer();
        server.service();
    }

    @Test
    public void clientTest() throws IOException {
        VoteTcpClient client = new VoteTcpClient();
        client.request(1, 1212);
    }
}