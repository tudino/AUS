package policom.protocol;

public class SubscriptionRequest extends Request {

    private final String topic;

    public SubscriptionRequest(String requestId, MessageType type, String topic) {
        super(requestId, type);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}