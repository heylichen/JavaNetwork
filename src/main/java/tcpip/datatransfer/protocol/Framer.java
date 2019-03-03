package tcpip.datatransfer.protocol;

import java.io.IOException;
import java.io.OutputStream;

public interface Framer {
    byte[] nextMsg() throws IOException;

    void frameMsg(byte[] msg, OutputStream os) throws IOException;
}
