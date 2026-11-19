package policom.sdk;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import policom.protocol.Message;
import policom.protocol.MessageType;

public class HeartbeatManager {

    private static final int INTERVAL_SECONDS = 10;
    private static final int TIMEOUT_SECONDS = 5;

    private final ConnectionManager connectionManager;
    private ScheduledExecutorService scheduler;

    public HeartbeatManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public synchronized void start() {
        if (scheduler != null && !scheduler.isShutdown()) {
            return;
        }

        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(
            this::sendHeartbeat,
            INTERVAL_SECONDS,
            INTERVAL_SECONDS,
            TimeUnit.SECONDS
        );
    }

    private void sendHeartbeat() {
        if (!connectionManager.isConnected()) {
            return;
        }

        try {
            Message response = connectionManager.sendRequest(
                MessageType.PING,
                null
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (response.getType() != MessageType.PONG) {
                connectionManager.close();
            }

        } catch (Exception e) {
            connectionManager.close();
        }
    }

    public synchronized void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
        }
    }
}
