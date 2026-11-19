package policom.protocol;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import policom.exceptions.ProtocolException;

public class Message {

    private static final int MAX_MESSAGE_SIZE = 1024 * 1024;

    private final MessageType msgType;
    private final String requestId;
    private final String payload;

    public Message(MessageType mtype, String requestId, String payload) {
        this.msgType = mtype;
        this.requestId = requestId;
        this.payload = payload;
    }

    public MessageType getType() {
        return msgType;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPayload() {
        return payload;
    }

    public void write(DataOutputStream out) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(buffer);

        writeString(data, msgType.name());
        writeString(data, requestId);
        writeString(data, payload);

        data.flush();

        byte[] bytes = buffer.toByteArray();

        if (bytes.length > MAX_MESSAGE_SIZE) {
            throw new ProtocolException("Message too large");
        }

        out.writeInt(bytes.length);
        out.write(bytes);
        out.flush();
    }

    public static Message read(DataInputStream in) throws IOException {
        int length = in.readInt();

        if (length <= 0 || length > MAX_MESSAGE_SIZE) {
            throw new ProtocolException("Invalid message length");
        }

        byte[] bytes = new byte[length];
        in.readFully(bytes);

        DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            MessageType mtype = MessageType.valueOf(readString(data));
            String requestId = readString(data);
            String payload = readString(data);

            if (data.available() != 0) {
                throw new ProtocolException("Unexpected message data");
            }

            return new Message(mtype, requestId, payload);

        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ProtocolException("Invalid message format", e);
        }
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        if (value == null) {
            out.writeInt(-1);
            return;
        }

        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();

        if (length == -1) {
            return null;
        }

        if (length < 0 || length > in.available()) {
            throw new ProtocolException("Invalid string length");
        }

        byte[] bytes = new byte[length];
        in.readFully(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    public static String encodePayload(String[] values) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(buffer);

            for (String value : values) {
                if (value == null) {
                    output.writeInt(-1);
                } else {
                    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
                    output.writeInt(bytes.length);
                    output.write(bytes);
                }
            }

            output.flush();

            return Base64.getEncoder().encodeToString(buffer.toByteArray());

        } catch (IOException e) {
            throw new IllegalStateException("Could not encode payload", e);
        }
    }

    public static String[] decodePayload(String payload, int fieldCount) throws IOException {
        if (payload == null) {
            throw new ProtocolException("Missing payload");
        }

        byte[] bytes = Base64.getDecoder().decode(payload);

        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));

        String[] values = new String[fieldCount];

        for (int i = 0; i < fieldCount; i++) {
            int length = input.readInt();

            if (length == -1) {
                values[i] = null;
                continue;
            }

            if (length < 0 || length > input.available()) {
                throw new ProtocolException("Invalid payload field length");
            }

            byte[] field = new byte[length];
            input.readFully(field);

            values[i] = new String(field, StandardCharsets.UTF_8);
        }

        if (input.available() != 0) {
            throw new ProtocolException("Unexpected payload data");
        }

        return values;
    }
}