package tcpip.datatransfer.protocol;


import java.io.*;

public class BinaryVoteCoder implements VoteCoder {
    private int ZERO = 0;

    @Override
    public byte[] requestToWire(VoteRequest voteRequest) {
        MsgFlags msgFlags = new MsgFlags(true, true, voteRequest.getRequestType() == 0);
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bao)) {
            dos.writeByte(msgFlags.toByte());
            dos.writeByte(ZERO);
            dos.writeInt(voteRequest.getCandidateId());
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VoteRequest requestFromWire(byte[] input) {
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(input))) {
            int flagByte = dis.read();
            MsgFlags flags = MsgFlags.fromByte(flagByte);
            if (flags == null || !flags.isMsg || !flags.isRequest) {
                return null;
            }
            int requestType = flags.isQueryRequest ? 0 : 1;
            dis.read();
            int candidateId = dis.readInt();

            return new VoteRequest(requestType, candidateId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public byte[] responseToWire(VoteResponse voteResponse) {
        MsgFlags msgFlags = new MsgFlags(true, false, false);

        try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bao)) {
            dos.writeByte(msgFlags.toByte());
            dos.writeByte(ZERO);
            dos.writeInt(voteResponse.getCandidateId());
            dos.writeLong(voteResponse.getVoteCount());
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VoteResponse responseFromWire(byte[] input) {
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(input))) {
            int flagByte = dis.read();
            MsgFlags flags = MsgFlags.fromByte(flagByte);
            if (flags == null || !flags.isMsg || flags.isRequest) {
                return null;
            }
            dis.read();
            int candidateId = dis.readInt();
            long voteCount = dis.readLong();
            return new VoteResponse(candidateId, voteCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * bits ops less than one byte
     * encapsulate first byte coding end decoding logic
     */
    private static class MsgFlags {
        private static final int MAGIC = 0b010101;
        private static final int BYTE_0 = 1;
        private static final int BYTE_1 = 1 << 1;
        private final boolean isMsg;
        private final boolean isRequest;
        private final boolean isQueryRequest;

        public MsgFlags(boolean isMsg, boolean isRequest, boolean isQueryRequest) {
            this.isMsg = isMsg;
            this.isRequest = isRequest;
            this.isQueryRequest = isQueryRequest;
        }

        public int toByte() {
            int magicAndFlags = MAGIC << 2;

            if (isRequest) {
                magicAndFlags &= ~BYTE_1;
                if (isQueryRequest) {
                    magicAndFlags &= ~BYTE_0;
                } else {
                    magicAndFlags |= BYTE_0;
                }
                return magicAndFlags;
            }
            magicAndFlags |= BYTE_1;
            magicAndFlags &= ~BYTE_0;
            return magicAndFlags;
        }

        private static MsgFlags fromByte(int flagByte) {
            if (flagByte >> 2 != MsgFlags.MAGIC) {
                return null;
            }
            boolean isRequest = (flagByte & BYTE_1) == 0;
            if (isRequest) {
                int requestType = flagByte & BYTE_0;
                boolean isQueryRequest = requestType == 0;
                return new MsgFlags(true, true, isQueryRequest);
            }
            return new MsgFlags(true, false, false);
        }
    }

    public static void main(String[] args) {
        System.out.println(0b01010110);
        VoteRequest vr = new VoteRequest(0, 2301);
        BinaryVoteCoder coder = new BinaryVoteCoder();
        byte[] brBytes = coder.requestToWire(vr);
        VoteRequest vrCopy = coder.requestFromWire(brBytes);
        System.out.println();

        VoteResponse resp = new VoteResponse(2301, 1212);
        byte[] respBytes = coder.responseToWire(resp);
        VoteResponse respCopy = coder.responseFromWire(respBytes);
        System.out.println();
    }
}
