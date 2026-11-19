package policom.broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import policom.keyvalue.KeyValueManager;
import policom.protocol.Message;
import policom.protocol.MessageType;
import policom.pubsub.PubSubManager;
import policom.pubsub.Subscription;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final BrokerConnectionManager connectionManager;
    private final PubSubManager pubSubManager;
    private final KeyValueManager keyValueManager;

    private DataInputStream input;
    private DataOutputStream output;

    public ClientHandler(Socket socket, BrokerConnectionManager connectionManager, PubSubManager pubSubManager, KeyValueManager keyValueManager) {
        this.socket = socket;
        this.connectionManager = connectionManager;
        this.pubSubManager = pubSubManager;
        this.keyValueManager = keyValueManager;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            System.out.println("Client connected: " + socket.getInetAddress());

            while (!socket.isClosed()) {
                Message message = Message.read(input);
                processMessage(message);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());

        } finally {
            pubSubManager.removeClient(this);
            close();
            connectionManager.remove(this);
        }
    }

    private void processMessage(Message message) throws IOException {
        try {
            switch (message.getType()) {
                case PING:
                    sendResponse(MessageType.PONG, message.getRequestId(), null);
                    break;
                case SUBSCRIBE: {
                    String[] fields = Message.decodePayload(message.getPayload(), 1);
                    pubSubManager.subscribe(fields[0], this);
                    sendResponse(MessageType.OK, message.getRequestId(), null);
                    break;
                }
                case UNSUBSCRIBE: {
                    String[] fields = Message.decodePayload(message.getPayload(), 1);
                    pubSubManager.unsubscribe(fields[0], this);
                    sendResponse(MessageType.OK, message.getRequestId(), null);
                    break;
                }
                case PUBLISH: {
                    String[] fields = Message.decodePayload(message.getPayload(), 2);
                    String topic = fields[0];
                    String content = fields[1];

                    for (Subscription subscription : pubSubManager.publish(topic)) {
                        subscription.getClientHandler().sendNotification(topic, content);
                    }

                    sendResponse(MessageType.ACK, message.getRequestId(), null);
                    break;
                }
                case SET: {
                    String[] fields = Message.decodePayload(message.getPayload(), 2);
                    keyValueManager.set(fields[0], fields[1]);
                    sendResponse(MessageType.OK, message.getRequestId(), null);
                    break;
                }
                case GET: {
                    String[] fields = Message.decodePayload(message.getPayload(), 1);
                    String value = keyValueManager.get(fields[0]);
                    sendResponse(MessageType.OK, message.getRequestId(), Message.encodePayload(new String[] { value }));
                    break;
                }
                case EXTEND_TTL: {
                    String[] fields = Message.decodePayload(message.getPayload(), 1);
                    keyValueManager.extendTTL(fields[0]);
                    sendResponse(MessageType.OK, message.getRequestId(), null);
                    break;
                }
                default:
                    sendError(message.getRequestId(), "Unsupported message type");
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            sendError(message.getRequestId(), e.getMessage());

        } catch (IOException e) {
            sendError(message.getRequestId(), "Invalid request payload");
        }
    }

    private void sendNotification(String topic, String content) throws IOException {
        Message notification = new Message(MessageType.NOTIFICATION, null, Message.encodePayload(new String[] { topic, content }));
        synchronized (this) {
            notification.write(output);
        }
    }

    private void sendResponse(MessageType type, String requestId, String payload) throws IOException {
        Message response = new Message(type, requestId, payload);
        synchronized (this) {
            response.write(output);
        }
    }

    private void sendError(String requestId, String error) throws IOException {
        sendResponse(MessageType.ERROR, requestId, error);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
