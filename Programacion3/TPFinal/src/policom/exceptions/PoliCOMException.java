package policom.exceptions;

public class PoliCOMException extends RuntimeException {

	private static final long serialVersionUID = -3565135940850600164L;

	public PoliCOMException(String message) {
        super(message);
    }

    public PoliCOMException(String message, Throwable cause) {
        super(message, cause);
    }
}