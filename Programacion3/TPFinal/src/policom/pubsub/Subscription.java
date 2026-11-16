package policom.pubsub;

import policom.broker.ClientHandler;

public class Subscription {

    private final String topic;
    private final ClientHandler clientHandler;

    public Subscription(String topic, ClientHandler clientHandler) {
        this.topic = topic;
        this.clientHandler = clientHandler;
    }

    public String getTopic() {
        return topic;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
