package policom.protocol;

public enum MessageType {

    // Solicitudes del cliente
    SUBSCRIBE,
    UNSUBSCRIBE,
    PUBLISH,
    SET,
    GET,

    // Respuestas del servidor
    ACK,
    RESPONSE,
    ERROR,

    // Control de conexión
    HEARTBEAT,
    HEARTBEAT_RESPONSE,

    // Notificaciones
    NOTIFICATION
}
