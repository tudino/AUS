package policom.protocol;

public enum MessageType {

    // Solicitudes del cliente
    SUBSCRIBE,
    UNSUBSCRIBE,
    PUBLISH,
    SET,
    GET,
    EXTEND_TTL,

    // Respuestas del servidor
    ACK,
    OK,
    ERROR,

    // Control de conexión
    PING,
    PONG,

    // Notificaciones
    NOTIFICATION
}
