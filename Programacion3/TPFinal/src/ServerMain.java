import java.io.IOException;

import policom.config.ServerConfig;
import policom.sdk.Server;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerConfig(5000));

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}