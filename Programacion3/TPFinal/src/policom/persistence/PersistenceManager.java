
package policom.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import policom.keyvalue.DataValue;

/*
 * Gestor de persistencia
 * Almacena el hashmap Clave-Valor en una estructura de archivos,
 * donde el nombre del archivo es la Clave, y el contenido es un DataValue que representa al Valor
 *
 * El archivo `__version__` contiene la version con la que se ha creado y trabaja el server
 * Cuando detecta diferente version vuelve a inicializar el directorio limpiando todo el contenido
 * 
 * Se limita a guardar, recuperar y eliminar datos del disco.
 */
public class PersistenceManager {

    private static final String VERSION_FILE = "__version__";
    private static final String PERSISTENCE_VERSION = "1.0";
    private final Path directory;
    private final String serverVersion;

    public PersistenceManager(Path directory) throws IOException {
        this(directory, PERSISTENCE_VERSION);
    }
    
    public PersistenceManager(Path directory, String serverVersion) throws IOException {
        if (directory == null || serverVersion == null || serverVersion.isEmpty()) {
            throw new IllegalArgumentException("Invalid persistence configuration");
        }

        this.directory = directory;
        this.serverVersion = serverVersion;

        initialize();
    }

    /*
     * Inicializa el directorio de persistencia
     */
    private void initialize() throws IOException {
        Files.createDirectories(directory);

        Path versionPath = directory.resolve(VERSION_FILE);

        if (Files.exists(versionPath)) {
            String storedVersion = new String(Files.readAllBytes(versionPath), StandardCharsets.UTF_8);

            if (!storedVersion.equals(serverVersion)) {
                clearStorage();
            }
        }

        writeVersion();
    }

    /*
     * Elimina todos los archivos a excepcion de `__version__` 
     */
    private void clearStorage() throws IOException {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(directory)) {
            for (Path file : files) {
                String name = file.getFileName().toString();

                if (!name.equals(VERSION_FILE)) {
                    Files.deleteIfExists(file);
                }
            }
        }
    }

    /*
     * Escribe la version actual en el archivo __version__ en una operacion atomica
     * usando un archivo temporal de por medio para evitar que la escritura quede incompleta
     */
    private void writeVersion() throws IOException {
        Path tempFile = Files.createTempFile(directory, "version-", ".tmp");

        try {
            Files.write(tempFile, serverVersion.getBytes(StandardCharsets.UTF_8));
            moveAtomically(tempFile, directory.resolve(VERSION_FILE));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /*
     * Guarda un DataValue en un archivo que lleva por nombre la key
     */
    public synchronized void save(String key, DataValue dataValue) throws IOException {
        validateKey(key);

        if (dataValue == null) {
            throw new IllegalArgumentException("DataValue cannot be null");
        }

        Path targetFile = directory.resolve(key);
        Path tempFile = Files.createTempFile(directory, key + "-", ".tmp");

        try {
            try (FileOutputStream fileOutput = new FileOutputStream(tempFile.toFile());
        		ObjectOutputStream output = new ObjectOutputStream(fileOutput)) {

                output.writeObject(dataValue);
                output.flush();
                fileOutput.getFD().sync();
            }

            moveAtomically(tempFile, targetFile);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /*
     * Obtiene el DataValue almacenado en un archivo
     */
    public synchronized DataValue load(String key) throws IOException {
        validateKey(key);

        Path file = directory.resolve(key);

        if (!Files.exists(file)) {
            return null;
        }

        try (FileInputStream fileInput = new FileInputStream(file.toFile());
             ObjectInputStream input = new ObjectInputStream(fileInput)) {

            Object object = input.readObject();

            if (!(object instanceof DataValue)) {
                throw new IOException("Invalid persisted data");
            }

            return (DataValue) object;

        } catch (ClassNotFoundException e) {
            throw new IOException("Could not deserialize persisted data", e);
        }
    }

    /*
     * Carga todos las entradas del directorio en memoria
     */
    public synchronized Map<String, DataValue> loadAll() throws IOException {
        Map<String, DataValue> data = new HashMap<>();

        try (DirectoryStream<Path> files = Files.newDirectoryStream(directory)) {
            for (Path file : files) {
                String key = file.getFileName().toString();

                if (key.equals(VERSION_FILE) || key.endsWith(".tmp")) {
                    continue;
                }

                validateKey(key);

                DataValue value = load(key);

                if (value != null) {
                    data.put(key, value);
                }
            }
        }

        return data;
    }

    /*
     * Elimina un archivo por su Clave
     */
    public synchronized void delete(String key) throws IOException {
        validateKey(key);
        Files.deleteIfExists(directory.resolve(key));
    }

    /*
     * Mueve un archivo atomicamente reemplazando al target en caso de que exista
     */
    private void moveAtomically(Path source, Path target) throws IOException {
        try {
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException e) {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /*
     * Valida que una Clave no sea null y ni este vacia
     */
    private void validateKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Invalid key");
        }
    }
}
