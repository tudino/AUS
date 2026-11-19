package policom.pubsub;

import policom.broker.ClientHandler;

/*
 * Entidad que representa una suscripcion, indicando topico y cliente
 */
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

    public ClientHandler getBrokerClientHandler() {
        return clientHandler;
    }
}
