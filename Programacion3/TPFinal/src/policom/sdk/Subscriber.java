package policom.sdk;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import policom.protocol.Message;
import policom.protocol.MessageType;

public class Subscriber {

    private static final int TIMEOUT_SECONDS = 5;

    private final ConnectionManager connectionManager;

    private final ConcurrentMap<String, Consumer<String>> subscriptions;

    public Subscriber(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.subscriptions = new ConcurrentHashMap<>();
    }

    public void subscribe(String topic, Consumer<String> listener)
            throws IOException {

        if (topic == null || topic.isEmpty() || listener == null) {
            throw new IllegalArgumentException("Invalid subscription");
        }

        subscriptions.put(topic, listener);

        try {
            Message response = connectionManager.sendRequest(
                MessageType.SUBSCRIBE,
                Message.encodePayload(new String[] { topic })
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            checkResponse(response);

        } catch (Exception e) {
            subscriptions.remove(topic, listener);
            throw new IOException("Could not subscribe to topic", e);
        }
    }

    public void unsubscribe(String topic) throws IOException {
        try {
            Message response = connectionManager.sendRequest(
                MessageType.UNSUBSCRIBE,
                Message.encodePayload(new String[] { topic })
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            checkResponse(response);
            subscriptions.remove(topic);

        } catch (Exception e) {
            throw new IOException("Could not unsubscribe from topic", e);
        }
    }

    void notify(Message message) {
        try {
            String[] fields = Message.decodePayload(message.getPayload(), 2);

            Consumer<String> listener = subscriptions.get(fields[0]);

            if (listener != null) {
                listener.accept(fields[1]);
            }

        } catch (IOException | RuntimeException e) {
            // Invalid notification or listener failure.
        }
    }

    private void checkResponse(Message response) throws IOException {
        if (response.getType() == MessageType.ERROR) {
            throw new IOException("Broker rejected the operation");
        }

        if (response.getType() != MessageType.OK) {
            throw new IOException("Unexpected broker response");
        }
    }
}
