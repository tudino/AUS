import policom.sdk.Client;
import policom.sdk.KeyValueClient;

public class Client_2 {

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 5000);

        client.connect();

        client.getSubscriber().subscribe("news", message ->
            System.out.println("Client 2 received: " + message)
        );

        System.out.println("Client 2 connected");

        //client.getPublisher().publish("news", "Hello from Client 2!");
        
        
        KeyValueClient kv = client.getKeyValueClient();
        System.out.println(kv.get("hola"));
        System.out.println(kv.get("uno"));
        System.out.println(kv.get("dos"));
        System.out.println(kv.get("tres"));
        
        while (client.isConnected()) {
        	// is alive
        }
        
        System.out.println("Bye Client 2");
    }
}