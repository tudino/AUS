package policom.sdk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import policom.protocol.Message;
import policom.protocol.MessageType;

/*
 * Proporciona acceso al servicio de almacenamiento clave-valor.
 * Permite almacenar y recuperar los valores mediante solicitudes enviadas al servidor.
 * Tambien permite renovar el tiempo de vida de los valores.
 */
public class KeyValueClient {

    private static final int TIMEOUT_SECONDS = 5;

    private final ConnectionManager connectionManager;

    public KeyValueClient(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void set(String key, String value) throws IOException {
        try {
            Message response = connectionManager.sendRequest(
                MessageType.SET,
                Message.encodePayload(new String[] { key, value })
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            checkResponse(response);

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Could not set key-value pair", e);
        }
    }

    public String get(String key) throws IOException {
        try {
            Message response = connectionManager.sendRequest(
                MessageType.GET,
                Message.encodePayload(new String[] { key })
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            checkResponse(response);

            return Message.decodePayload(response.getPayload(), 1)[0];

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Could not get value", e);
        }
    }

    public String extendTTL(String key) throws IOException {
    	try {
            Message response = connectionManager.sendRequest(
                MessageType.EXTEND_TTL,
                Message.encodePayload(new String[] { key })
            ).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            checkResponse(response);

            return Message.decodePayload(response.getPayload(), 1)[0];

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Could not get value", e);
        }
    }
    
    private void checkResponse(Message response) throws IOException {
        if (response.getType() == MessageType.ERROR) {
            throw new IOException("Broker rejected the operation: " + response.getPayload());
        }

        if (response.getType() != MessageType.OK) {
            throw new IOException("Unexpected broker response " + response.getPayload());
        }
    }
}
