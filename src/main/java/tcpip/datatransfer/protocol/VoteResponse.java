package tcpip.datatransfer.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteResponse {
    private int candidateId;
    private long voteCount;

    public VoteResponse(int candidateId, long voteCount) {
        this.candidateId = candidateId;
        this.voteCount = voteCount;
    }
}