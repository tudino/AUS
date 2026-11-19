
package policom.sdk;

import java.util.concurrent.CompletableFuture;

import policom.protocol.Message;
import policom.protocol.MessageType;

/*
 * Gestiona el envío periódico de mensajes Heartbeat para verificar
 * que la conexión con el servidor permanece activa.
 * Si detecta un fallo de comunicación, cierra la conexión.
 */
public class HeartbeatManager {

    private static final int INTERVAL_SECONDS = 10;
    private static final int TIMEOUT_SECONDS = 5;

    private final ConnectionManager connectionManager;
    private Thread heartbeatThread;

    public HeartbeatManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public synchronized void start() {
        if (heartbeatThread != null && heartbeatThread.isAlive()) {
            return;
        }

        heartbeatThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	while (!Thread.currentThread().isInterrupted()) {
            		try {
            			Thread.sleep(INTERVAL_SECONDS * 1000);
            			sendHeartbeat();
            		} catch (InterruptedException e) {
            			Thread.currentThread().interrupt();
            		}
            	}
            }
        });

        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    private void sendHeartbeat() {
        if (!connectionManager.isConnected()) {
            return;
        }

        try {
        	CompletableFuture<Message> future = connectionManager.sendRequest(MessageType.PING, null);
            Message response = future.get(TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS);

            if (response.getType() != MessageType.PONG) {
                connectionManager.close();
            }

        } catch (Exception e) {
            connectionManager.close();
        }
    }

    public synchronized void stop() {
        if (heartbeatThread != null) {
            heartbeatThread.interrupt();
            heartbeatThread = null;
        }
    }
}
