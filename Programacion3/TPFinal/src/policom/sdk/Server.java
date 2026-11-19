package policom.sdk;

import java.io.IOException;

import policom.broker.BrokerServer;
import policom.config.ServerConfig;

/*
 * Representa el servidor de PoliCOM y proporciona una interfaz para su puesta en marcha.
 * Permite configurar, iniciar y detener el servidor que ofrece los servicios
 * de publicación/suscripción y almacenamiento clave-valor.
 */
public class Server {

    private final BrokerServer brokerServer;

    public Server(int port) throws IOException {
        this.brokerServer = new BrokerServer(port);
    }
    
    public Server(ServerConfig config) throws IOException {
        if (config == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }

        this.brokerServer = new BrokerServer(config.getPort());
    }

    public void start() throws IOException {
        brokerServer.start();
    }

    public void stop() {
        brokerServer.stop();
    }

    public boolean isRunning() {
        return brokerServer.isRunning();
    }
}
