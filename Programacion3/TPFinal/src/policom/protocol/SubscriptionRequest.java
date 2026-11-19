package policom.protocol;

public class SubscriptionRequest extends Request {

    private final String topic;

    public SubscriptionRequest(String requestId, MessageType mtype, String topic) {
        super(requestId, mtype);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}