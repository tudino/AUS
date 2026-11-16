import policom.sdk.Client;
import policom.sdk.KeyValueClient;

public class Client_1 {

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 5000);

        client.connect();

        KeyValueClient kv = client.getKeyValueClient();
        kv.set("hola", "mundo");
        kv.set("uno", "ga");
        kv.set("dos", "ga");
        kv.set("tres", "ga");
        
        System.out.println("Bye Client 1");
        client.disconnect();
    }
}