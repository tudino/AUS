package policom.protocol;

public abstract class Request {

    private final String requestId;
    private final MessageType msgType;

    protected Request(String requestId, MessageType mtype) {
        this.requestId = requestId;
        this.msgType = mtype;
    }

    public String getRequestId() {
        return requestId;
    }

    public MessageType getType() {
        return msgType;
    }
}