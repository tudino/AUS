
package policom.keyvalue;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import policom.persistence.PersistenceManager;

/*
 * Gestor del almacenamiento Clave-Valor en memoria y de forma persistente.
 * Requiere PersistenceManager en caso de utilizar el modulo de persistencia.
 * Al invocar el constructor se cargan los datos persistentes en memoria y eliminan los elementos expirados
 */
public class KeyValueManager {

    private static final int MAX_KEY_LENGTH = 32;
    private static final int MAX_VALUE_LENGTH = 256;

    private final ConcurrentMap<String, DataValue> storage;
    private final PersistenceManager persistenceManager;
    private final int keyValueTTL;

    public KeyValueManager(PersistenceManager persistenceManager, int keyValueTTL) throws IOException {
        if (keyValueTTL < 0) {
            throw new IllegalArgumentException("TTL cannot be negative");
        }

        this.storage = new ConcurrentHashMap<>();
        this.persistenceManager = persistenceManager;
        this.keyValueTTL = keyValueTTL;

        if (persistenceManager != null) {
            Map<String, DataValue> persistedData = persistenceManager.loadAll();

            for (Map.Entry<String, DataValue> entry : persistedData.entrySet()) {
                if (isExpired(entry.getValue())) {
                    persistenceManager.delete(entry.getKey());
                } else {
                    storage.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /*
     * Almacena una clave con su valor envuelto en DataValue.
     * Si el TTL esta habilitado, calcula su tiempo de expiracion.
     */
    public synchronized void set(String key, String value) throws IOException {
        validateKey(key);
        validateValue(value);

        Instant now = Instant.now();
        Instant expirationTime = keyValueTTL > 0 ? now.plusSeconds(keyValueTTL) : null;

        DataValue dataValue = new DataValue(value, expirationTime, now);

        if (persistenceManager != null) {
            persistenceManager.save(key, dataValue);
        }

        storage.put(key, dataValue);
    }

    /*
     * Obtiene el valor asociado a una clave.
     * Si la clave vencio, la elimina del almacenamiento.
     */
    public synchronized String get(String key) throws IOException {
        validateKey(key);

        DataValue dataValue = storage.get(key);

        if (dataValue == null) {
            return null;
        }

        if (isExpired(dataValue)) {
            storage.remove(key);

            if (persistenceManager != null) {
                persistenceManager.delete(key);
            }

            return null;
        }

        return dataValue.getValue();
    }
    
    /*
     * Extiende el tiempo de expiracion de una clave
     */
    public synchronized void extendTTL(String key) throws IOException {
    	if (keyValueTTL == 0) {
            return;
        }
    	
        validateKey(key);

        DataValue dataValue = storage.get(key);

        if (dataValue == null || isExpired(dataValue)) {
            return;
        }

        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(keyValueTTL);
        
        DataValue updatedValue = new DataValue(dataValue.getValue(), expirationTime, now);

        if (persistenceManager != null) {
            persistenceManager.save(key, updatedValue);
        }

        storage.put(key, updatedValue);
    }

    private boolean isExpired(DataValue dataValue) {
        Instant expirationTime = dataValue.getExpirationTime();

        return expirationTime != null && !expirationTime.isAfter(Instant.now());
    }

    private void validateKey(String key) {
        if (key == null || key.isEmpty() || !key.matches("[a-zA-Z0-9_-]")) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        if (key.length() > MAX_KEY_LENGTH) {
            throw new IllegalArgumentException("Key exceeds maximum length of " + MAX_KEY_LENGTH + " characters");
        }
    }

    private void validateValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }

        if (value.length() > MAX_VALUE_LENGTH) {
            throw new IllegalArgumentException("Value exceeds maximum length of " + MAX_VALUE_LENGTH + " characters");
        }
    }
}
