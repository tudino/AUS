package policom.keyvalue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class KeyValueManager {

    private static final int MAX_KEY_LENGTH = 32;
    private static final int MAX_VALUE_LENGTH = 256;

    private final ConcurrentMap<String, String> storage;

    public KeyValueManager() {
        this.storage = new ConcurrentHashMap<>();
    }

    public void set(String key, String value) {
        validateKey(key);
        validateValue(value);
        storage.put(key, value);
    }

    public String get(String key) {
        validateKey(key);
        return storage.get(key);
    }

    private void validateKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        if (key.length() > MAX_KEY_LENGTH) {
            throw new IllegalArgumentException("Key exceeds maximum length of 32 characters");
        }
    }

    private void validateValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }

        if (value.length() > MAX_VALUE_LENGTH) {
            throw new IllegalArgumentException("Value exceeds maximum length of 256 characters");
        }
    }
}
