package policom.pubsub;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import policom.broker.ClientHandler;

public class PubSubManager {

    private final ConcurrentMap<String, Topic> topics;

    public PubSubManager() {
        this.topics = new ConcurrentHashMap<>();
    }

    public void subscribe(String topicName, ClientHandler clientHandler) {
        validateTopic(topicName);

        topics.compute(topicName, (name, topic) -> {
            if (topic == null) {
                topic = new Topic(name);
            }

            topic.subscribe(clientHandler);
            return topic;
        });
    }

    public void unsubscribe(String topicName, ClientHandler clientHandler) {
        validateTopic(topicName);

        topics.computeIfPresent(topicName, (_, topic) -> {
            topic.unsubscribe(clientHandler);
            return topic.isEmpty() ? null : topic;
        });
    }

    public Set<Subscription> publish(String topicName) {
        validateTopic(topicName);

        Topic topic = topics.get(topicName);

        if (topic == null) {
            return Collections.emptySet();
        }

        return topic.getSubscriptions();
    }

    public void removeClient(ClientHandler clientHandler) {
        topics.forEach((topicName, _) ->
            unsubscribe(topicName, clientHandler)
        );
    }

    private void validateTopic(String topicName) {
        if (topicName == null || topicName.isEmpty()) {
            throw new IllegalArgumentException("Topic cannot be null or empty");
        }
    }
}
