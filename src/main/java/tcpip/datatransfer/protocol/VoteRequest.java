package tcpip.datatransfer.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {
    private int requestType; //0: inquiry, 1: vote
    private int candidateId;

    public VoteRequest(int requestType, int candidateId) {
        this.requestType = requestType;
        this.candidateId = candidateId;
    }
}
