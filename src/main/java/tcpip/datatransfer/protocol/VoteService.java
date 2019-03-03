package tcpip.datatransfer.protocol;

import java.util.HashMap;
import java.util.Map;

public class VoteService {
    private Map<Integer, Long> map = new HashMap();

    public VoteResponse handleRequest(VoteRequest request) {
        if (request == null) {
            return null;
        }
        Long voteCount = map.get(request.getCandidateId());
        if (voteCount == null) {
            voteCount = 0L;
            map.put(request.getCandidateId(), voteCount);
        }
        if (request.getRequestType() == 1) {
            voteCount += 1;
            map.put(request.getCandidateId(), voteCount);
        }
        return new VoteResponse(request.getCandidateId(), voteCount);
    }
}
