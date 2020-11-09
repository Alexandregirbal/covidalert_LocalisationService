package polytech.covidalert.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import polytech.covidalert.models.UserLocation;

@Service
public class KafkaProducer {

    private KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    KafkaProducer(KafkaTemplate<String,Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(Object message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }
}
