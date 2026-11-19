package policom.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import policom.protocol.Message;
import policom.protocol.MessageType;

public class ConnectionManager {

    private final String host;
    private final int port;

    private final ConcurrentMap<String, CompletableFuture<Message>> pendingRequests;

    private volatile Socket socket;
    private volatile DataInputStream input;
    private volatile DataOutputStream output;
    private volatile boolean connected;

    private Consumer<Message> notificationListener;

    public ConnectionManager(String host, int port) {
        this.host = host;
        this.port = port;
        this.pendingRequests = new ConcurrentHashMap<>();
    }

    public void connect() throws IOException {
        if (connected) {
            return;
        }

        Socket newSocket = new Socket(host, port);

        socket = newSocket;
        input = new DataInputStream(newSocket.getInputStream());
        output = new DataOutputStream(newSocket.getOutputStream());
        connected = true;

        Thread receiver = new Thread(
            this::receiveMessages,
            "PoliCOM-Receiver"
        );

        receiver.setDaemon(true);
        receiver.start();
    }

    public CompletableFuture<Message> sendRequest(MessageType type, String payload) throws IOException {
        if (!connected) {
            throw new IOException("Client is disconnected");
        }

        String requestId = UUID.randomUUID().toString();

        CompletableFuture<Message> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        Message message = new Message(type, requestId, payload);

        try {
            synchronized (this) {
                if (!connected) {
                    throw new IOException("Client is disconnected");
                }

                message.write(output);
            }
        } catch (IOException e) {
            pendingRequests.remove(requestId);
            future.completeExceptionally(e);
            throw e;
        }

        return future;
    }

    private void receiveMessages() {
        try {
            while (connected) {
                Message message = Message.read(input);

                if (message.getType() == MessageType.NOTIFICATION) {
                    Consumer<Message> listener = notificationListener;

                    if (listener != null) {
                        listener.accept(message);
                    }

                    continue;
                }

                CompletableFuture<Message> future =
                    pendingRequests.remove(message.getRequestId());

                if (future != null) {
                    future.complete(message);
                }
            }
        } catch (IOException e) {
            close();
        }
    }

    public void setNotificationListener(Consumer<Message> listener) {
        this.notificationListener = listener;
    }

    public boolean isConnected() {
        return connected;
    }

    public void close() {
        connected = false;

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
        }

        IOException exception = new IOException("Connection closed");

        pendingRequests.forEach((_, future) ->
            future.completeExceptionally(exception)
        );

        pendingRequests.clear();
    }
}
