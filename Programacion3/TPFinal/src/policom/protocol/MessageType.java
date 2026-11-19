package policom.protocol;

/*
 * Tipos de mensajes transmitidos entre cliente-servidor
 */
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

    // Recepcion de mensajes en un canal suscrito
    SUBSCRIPTION_EVENT
}
