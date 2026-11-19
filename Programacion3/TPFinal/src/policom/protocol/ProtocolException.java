package policom.protocol;

import java.io.IOException;

public class ProtocolException extends IOException {

	private static final long serialVersionUID = -7877861992452436185L;

	public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}