package policom.protocol;

public class KeyValueRequest extends Request {

    private final String key;
    private final String value;

    public KeyValueRequest(String requestId, MessageType mtype, String key, String value) {
        super(requestId, mtype);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}