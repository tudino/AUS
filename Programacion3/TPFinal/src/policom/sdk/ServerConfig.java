package policom.sdk;

public class ServerConfig {

    private static final int DEFAULT_PORT = 5000;

    private final int port;

    public ServerConfig() {
        this(DEFAULT_PORT);
    }

    public ServerConfig(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid port");
        }

        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
