package policom.sdk;

import java.io.IOException;

public class Client {

    private final ConnectionManager connectionManager;
    private final HeartbeatManager heartbeatManager;
    private final PendingMessagesQueue pendingMessagesQueue;

    private final Publisher publisher;
    private final Subscriber subscriber;
    private final KeyValueClient keyValueClient;

    public Client(String host, int port) {
        this(host, port, 100);
    }

    public Client(String host, int port, int pendingQueueCapacity) {
        this.connectionManager = new ConnectionManager(host, port);
        this.heartbeatManager = new HeartbeatManager(connectionManager);
        this.pendingMessagesQueue = new PendingMessagesQueue(
            pendingQueueCapacity
        );

        this.publisher = new Publisher(
            connectionManager,
            pendingMessagesQueue
        );

        this.subscriber = new Subscriber(connectionManager);

        this.keyValueClient = new KeyValueClient(connectionManager);

        connectionManager.setNotificationListener(subscriber::notify);
    }

    public void connect() throws IOException {
        connectionManager.connect();
        heartbeatManager.start();
        flushPendingMessages();
    }

    public void disconnect() {
        heartbeatManager.stop();
        connectionManager.close();
    }

    public boolean isConnected() {
        return connectionManager.isConnected();
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public KeyValueClient getKeyValueClient() {
        return keyValueClient;
    }

    public int getPendingMessageCount() {
        return pendingMessagesQueue.size();
    }

    private void flushPendingMessages() {
        PendingMessagesQueue.PendingMessage pending;

        while (connectionManager.isConnected() && (pending = pendingMessagesQueue.poll()) != null) {
            try {
                publisher.send(pending.getTopic(), pending.getMessage());

            } catch (IOException e) {
                pendingMessagesQueue.requeue(pending);
                break;
            }
        }
    }
}
