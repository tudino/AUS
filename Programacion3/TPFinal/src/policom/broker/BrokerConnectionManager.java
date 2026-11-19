package policom.broker;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerConnectionManager {

    private final Set<ClientHandler> connections;

    public BrokerConnectionManager() {
        connections = ConcurrentHashMap.newKeySet();
    }

    public void add(ClientHandler handler) {
        connections.add(handler);
    }

    public void remove(ClientHandler handler) {
        connections.remove(handler);
    }

    public int getConnectionCount() {
        return connections.size();
    }

    public void closeAll() {

        for (ClientHandler handler : connections) {
            handler.close();
        }

        connections.clear();
    }
}
