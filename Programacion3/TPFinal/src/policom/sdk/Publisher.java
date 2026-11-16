package policom.sdk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import policom.protocol.Message;
import policom.protocol.MessageType;

public class Publisher {

    private static final int TIMEOUT_SECONDS = 5;

    private final ConnectionManager connectionManager;
    private final PendingMessagesQueue pendingMessagesQueue;

    public Publisher(ConnectionManager connectionManager, PendingMessagesQueue pendingMessagesQueue) {
        this.connectionManager = connectionManager;
        this.pendingMessagesQueue = pendingMessagesQueue;
    }

    public void publish(String topic, String message) throws IOException {
        if (topic == null || topic.isEmpty()) {
            throw new IllegalArgumentException("Topic cannot be null or empty");
        }

        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        if (!connectionManager.isConnected()) {
            if (!pendingMessagesQueue.offer(topic, message)) {
                throw new IllegalStateException("Pending messages queue is full");
            }

            return;
        }

        send(topic, message);
    }

    void send(String topic, String message) throws IOException {
        try {
            Message response = connectionManager.sendRequest(
                MessageType.PUBLISH,
                Message.encodePayload(new String[] { topic, message })
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (response.getType() != MessageType.ACK) {
                throw new IOException("Publication was not acknowledged");
            }

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Could not publish message", e);
        }
    }
}
