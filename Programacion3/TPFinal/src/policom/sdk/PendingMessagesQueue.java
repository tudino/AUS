package policom.sdk;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PendingMessagesQueue {

    private final BlockingQueue<PendingMessage> queue;

    public PendingMessagesQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public boolean offer(String topic, String message) {
        return queue.offer(new PendingMessage(topic, message));
    }

    public PendingMessage poll() {
        return queue.poll();
    }

    public void requeue(PendingMessage message) {
        if (!queue.offer(message)) {
            throw new IllegalStateException("Pending messages queue is full");
        }
    }

    public int size() {
        return queue.size();
    }

    public static class PendingMessage {
        private final String topic;
        private final String message;

        public PendingMessage(String topic, String message) {
            this.topic = topic;
            this.message = message;
        }

        public String getTopic() {
            return topic;
        }

        public String getMessage() {
            return message;
        }
    }
}
