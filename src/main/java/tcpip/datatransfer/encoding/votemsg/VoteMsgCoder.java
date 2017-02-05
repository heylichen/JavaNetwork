package tcpip.datatransfer.encoding.votemsg;

import tcpip.socket.cases.votemsg.VoteMsg;

import java.io.IOException;

public interface VoteMsgCoder {

  byte[] toWire(VoteMsg msg) throws IOException;

  VoteMsg fromWire(byte[] input) throws IOException;
}