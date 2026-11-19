package policom_cli;

import java.io.IOException;
import java.util.Scanner;

import policom.sdk.Client;

public class Main {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        if (args.length == 2 && args[0].equals("-p")) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port");
                return;
            }
        }

        Client client = new Client(host, port);

        try {
            client.connect();
            System.out.println("Connected to PoliCOM on port " + port);

            CommandHandler handler = new CommandHandler(client);
            
            try (Scanner scanner = new Scanner(System.in)) {

	            while (true) {
	                String command = scanner.nextLine();
	
	                if (command.equalsIgnoreCase("EXIT")) {
	                    break;
	                }
	                
	                handler.execute(command);
	            }
	
	            client.disconnect();
	            System.out.println("Disconnected");
            } catch (Exception e) {
                System.out.println("Application error: " + e.getMessage());
            } 
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
