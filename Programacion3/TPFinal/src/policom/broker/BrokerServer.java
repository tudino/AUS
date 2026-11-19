package policom.broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import policom.config.ServerConfig;
import policom.keyvalue.KeyValueManager;
import policom.persistence.PersistenceManager;
import policom.pubsub.PubSubManager;

/*
 * Gestiona las conexiones de los clientes y coordina los módulos de Pub/Sub y Key-Value.
 * Administra el ciclo de vida del broker y su configuración.
 */
public class BrokerServer {

    private final ServerConfig serverConfig;
    private final BrokerConnectionManager connectionManager;
    private final PubSubManager pubSubManager;
    private final KeyValueManager keyValueManager;

    private ServerSocket serverSocket;
    private volatile boolean running;

    public BrokerServer(int port) throws IOException {
    	ServerConfig svConf = new ServerConfig(port);
    	this(svConf);
    }
    
    public BrokerServer(ServerConfig serverConfig) throws IOException {
        this.serverConfig = serverConfig;
        this.connectionManager = new BrokerConnectionManager();
        this.pubSubManager = new PubSubManager();
        PersistenceManager persistenceManager = null;
        
        if (serverConfig.isPersistenceEnabled()) {
        	if (serverConfig.getPersistencePath() == null) {
            	Path persistencePath = Paths.get(System.getProperty("user.home"), ".policom", "server-" + serverConfig.getPort());
            	serverConfig.setPersistencePath(persistencePath);
            }
        	
            persistenceManager = new PersistenceManager(serverConfig.getPersistencePath());
        }

        this.keyValueManager = new KeyValueManager(persistenceManager, serverConfig.getKeyValueTTL());
    }

    public boolean isRunning() {
        return running;
    }

    /*
     * Bucle de espera de solicitudes, por cada conexion lanza un nuevo hilo 
     */
    public void start() throws IOException {
        if (running) {
            throw new IllegalStateException("Server already running");
        }

        serverSocket = new ServerSocket(serverConfig.getPort());
        running = true;

        System.out.println("Broker started on port " + serverConfig.getPort());

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

    /*
     * Frena y cierra el server socket
     * Cierra todos los socket de clientes
     */
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
}
