package policom.keyvalue;

import java.io.Serializable;
import java.time.Instant;

public class DataValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String value;
    private final Instant expirationTime;
    private final Instant lastUpdate;

    public DataValue(String value, Instant expirationTime, Instant lastUpdate) {
        this.value = value;
        this.expirationTime = expirationTime;
        this.lastUpdate = lastUpdate;
    }

    public String getValue() {
        return value;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }
}