package policom.broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import policom.keyvalue.KeyValueManager;
import policom.pubsub.PubSubManager;

public class BrokerServer {

    private final int port;
    private final ConnectionManager connectionManager;
    private final PubSubManager pubSubManager;
    private final KeyValueManager keyValueManager;

    private ServerSocket serverSocket;
    private volatile boolean running;

    public BrokerServer(int port) {
        this.port = port;
        this.connectionManager = new ConnectionManager();
        this.pubSubManager = new PubSubManager();
        this.keyValueManager = new KeyValueManager();
    }

    public void start() throws IOException {
        if (running) {
            throw new IllegalStateException("Server already running");
        }

        serverSocket = new ServerSocket(port);
        running = true;

        System.out.println("Broker started on port " + port);

        try {
            while (running) {
                Socket clientSocket = serverSocket.accept();

                ClientHandler handler = new ClientHandler(
                    clientSocket,
                    connectionManager,
                    pubSubManager,
                    keyValueManager
                );

                connectionManager.add(handler);

                Thread thread = new Thread(
                    handler,
                    "Client-" + clientSocket.getPort()
                );

                thread.start();
            }

        } catch (IOException e) {
            if (running) {
                throw e;
            }
        } finally {
            stop();
        }
    }

    public synchronized void stop() {
        running = false;

        try {
            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionManager.closeAll();

        System.out.println("Broker stopped");
    }

    public boolean isRunning() {
        return running;
    }
}
