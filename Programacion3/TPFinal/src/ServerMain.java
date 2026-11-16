import policom.sdk.Server;
import policom.sdk.ServerConfig;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server(new ServerConfig(5000));

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}