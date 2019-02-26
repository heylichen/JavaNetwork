package tcpip.datatransfer.protocol;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class StringVoteCoder implements VoteCoder {
    /*
     * Wire Format
     * request: "MAGIC REQUEST_FLAG REQUEST_INQUIRY|REQUEST_VOTE"
     * response "MAGIC RESPONSE_FLAG candidateId voteCount"
     * Charset is fixed by the wire format.
     */
    public static final String MAGIC = "Voting";
    public static final String REQUEST_FLAG = "REQ";
    public static final String RESPONSE_FLAG = "RES";
    public static final String REQUEST_INQUIRY = "0";
    public static final String REQUEST_VOTE = "1";
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final String DELIMITER = " ";
    public static final int MAX_WIRE_LENGTH = 2000;

    @Override
    public byte[] requestToWire(VoteRequest voteRequest) {
        StringBuilder sb = new StringBuilder();
        appendWithMore(sb, MAGIC);
        appendWithMore(sb, REQUEST_FLAG);
        String requestType = String.valueOf(voteRequest.getRequestType());
        appendWithMore(sb, requestType);
        sb.append(String.valueOf(voteRequest.getCandidateId()));
        return sb.toString().getBytes(CHARSET);
    }

    private void appendWithMore(StringBuilder sb, String content) {
        sb.append(content).append(DELIMITER);
    }

    @Override
    public VoteRequest requestFromWire(byte[] input) {
        if (input == null || input.length == 0) {
            return null;
        }
        ByteArrayInputStream bai = new ByteArrayInputStream(input);
        InputStreamReader ir = new InputStreamReader(bai, CHARSET);
        Scanner scanner = new Scanner(ir);

        if (!MAGIC.equals(next(scanner))) {
            return null;
        }
        if (!REQUEST_FLAG.equals(next(scanner))) {
            return null;
        }
        String requestType = next(scanner);
        if (!REQUEST_INQUIRY.equals(requestType) && !REQUEST_VOTE.equals(requestType)) {
            return null;
        }
        int requestTypeInt = Integer.parseInt(requestType);
        String candidateIdStr = next(scanner);
        if (candidateIdStr == null) {
            return null;
        }
        int candidateId = Integer.parseInt(candidateIdStr);
        return new VoteRequest(requestTypeInt, candidateId);
    }

    private String next(Scanner scanner) {
        return scanner.hasNext() ? scanner.next() : null;
    }


    @Override
    public byte[] responseToWire(VoteResponse voteResponse) {
        StringBuilder sb = new StringBuilder();
        appendWithMore(sb, MAGIC);
        appendWithMore(sb, RESPONSE_FLAG);
        appendWithMore(sb, String.valueOf(voteResponse.getCandidateId()));
        sb.append(String.valueOf(voteResponse.getVoteCount()));
        return sb.toString().getBytes(CHARSET);
    }

    @Override
    public VoteResponse responseFromWire(byte[] input) {
        if (input == null || input.length == 0) {
            return null;
        }
        ByteArrayInputStream bai = new ByteArrayInputStream(input);
        InputStreamReader ir = new InputStreamReader(bai, CHARSET);
        Scanner scanner = new Scanner(ir);

        if (!MAGIC.equals(next(scanner))) {
            return null;
        }
        if (!RESPONSE_FLAG.equals(next(scanner))) {
            return null;
        }
        String candidateIdStr = next(scanner);
        if (candidateIdStr == null) {
            return null;
        }
        int candidateId = Integer.parseInt(candidateIdStr);

        String voteCountStr = next(scanner);
        if (voteCountStr == null) {
            return null;
        }
        long voteCount = Long.parseLong(voteCountStr);

        return new VoteResponse(candidateId, voteCount);
    }

    public static void main(String[] args) {
        StringVoteCoder coder = new StringVoteCoder();
        VoteRequest req = new VoteRequest(1,1120);
        byte[] bytes = coder.requestToWire(req);
        VoteRequest req2 = coder.requestFromWire(bytes);


        VoteResponse vr = new VoteResponse(1220, 5232);
        byte[] bytesRes = coder.responseToWire(vr);
        VoteResponse vr2 = coder.responseFromWire(bytesRes);
        System.out.println();
    }
}
