package policom.protocol;

public abstract class Request {

    private final String requestId;
    private final MessageType type;

    protected Request(String requestId, MessageType type) {
        this.requestId = requestId;
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public MessageType getType() {
        return type;
    }
}