package policom_cli;

import java.io.IOException;
import policom.sdk.Client;

public class CommandHandler {

    private final Client client;

    public CommandHandler(Client client) {
        this.client = client;
    }

    public void execute(String command) {
        String[] parts = command.split(" ", 3);
        
        try {
            switch (parts[0].toUpperCase()) {

                case "SET":
                    if (parts.length < 3) break;
                    client.getKeyValueClient().set(parts[1], parts[2]);
                    System.out.println("OK");
                    break;

                case "GET":
                    if (parts.length < 2) break;
                    String value = client.getKeyValueClient().get(parts[1]);
                    System.out.println(value == null ? "(null)" : value);
                    break;

                case "EXTEND":
                    if (parts.length < 2) break;
                    client.getKeyValueClient().extendTTL(parts[1]);
                    System.out.println("OK");
                    break;

                case "SUB":
                    if (parts.length < 2) break;
                    client.getSubscriber().subscribe(parts[1], message -> System.out.println(message));
                    System.out.println("Subscribed to " + parts[1]);
                    break;

                case "UNSUB":
                    if (parts.length < 2) break;
                    client.getSubscriber().unsubscribe(parts[1]);
                    System.out.println("Unsubscribed from " + parts[1]);
                    break;

                case "PUB":
                    if (parts.length < 3) break;
                    client.getPublisher().publish(parts[1], parts[2]);
                    System.out.println("Published");
                    break;

                default:
                    System.out.println("Unknown command");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
