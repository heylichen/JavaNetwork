package tcpip.datatransfer.protocol;

/**
 * vote msg protocol, separating request and response msg
 */
public interface VoteCoder {

    byte[] requestToWire(VoteRequest voteRequest);

    VoteRequest requestFromWire(byte[] input);

    byte[] responseToWire(VoteResponse voteResponse);

    VoteResponse responseFromWire(byte[] input);
}
