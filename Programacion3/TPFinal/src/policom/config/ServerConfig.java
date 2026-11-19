package policom.config;

import java.nio.file.Path;

public class ServerConfig {

    private static final int DEFAULT_PORT = 5000;
    private int port;
    private boolean persistenceEnabled = false;
    private Path persistencePath = null;
    private int keyValueTTL = 0;

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

	public boolean isPersistenceEnabled() {
		return persistenceEnabled;
	}

	public void setPersistenceEnabled(boolean persistenceEnabled) {
		this.persistenceEnabled = persistenceEnabled;
	}

	public Path getPersistencePath() {
		return persistencePath;
	}

	public void setPersistencePath(Path persistencePath) {
		this.persistencePath = persistencePath;
	}

	public int getKeyValueTTL() {
		return keyValueTTL;
	}

	public void setKeyValueTTL(int keyValueTTL) {
		this.keyValueTTL = keyValueTTL;
	}
}
