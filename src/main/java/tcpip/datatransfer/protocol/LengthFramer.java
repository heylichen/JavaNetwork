package tcpip.datatransfer.protocol;

import java.io.*;

public class LengthFramer implements Framer {
    private final InputStream inputStream;

    public LengthFramer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public byte[] nextMsg() throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
        int length;
        try {
            length = dis.readUnsignedShort();
        } catch (IOException e) {
            return null;
        }
        byte[] bytes = new byte[length];
        dis.readFully(bytes);
        return bytes;
    }

    @Override
    public void frameMsg(byte[] msg, OutputStream os) throws IOException {
        int length = msg.length;
        DataOutputStream dis = new DataOutputStream(new BufferedOutputStream(os));
        dis.writeShort(length);
        dis.write(msg);
        dis.flush();
    }
}
