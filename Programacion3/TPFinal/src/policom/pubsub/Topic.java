package policom.pubsub;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import policom.broker.ClientHandler;

/*
 * Entidad que establece una relacion entre el nombre de un topico y su tabla de suscripciones.
 * Este recurso permite rutear las publicaciones en un topico a todos sus suscriptores.
 */
public class Topic {

    private final String name;
    private final ConcurrentMap<ClientHandler, Subscription> subscriptions;

    public Topic(String name) {
        this.name = name;
        this.subscriptions = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void subscribe(ClientHandler clientHandler) {
        subscriptions.computeIfAbsent(clientHandler, handler -> new Subscription(name, handler));
    }

    public void unsubscribe(ClientHandler clientHandler) {
        subscriptions.remove(clientHandler);
    }

    public Set<Subscription> getSubscriptions() {
        return new HashSet<>(subscriptions.values());
    }

    public boolean isEmpty() {
        return subscriptions.isEmpty();
    }
}
