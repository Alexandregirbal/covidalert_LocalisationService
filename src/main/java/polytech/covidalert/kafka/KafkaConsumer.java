package polytech.covidalert.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import polytech.covidalert.models.UserLocation;

@Component
public class KafkaConsumer {

    Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "mytopic-1", groupId="group_id")
    public void listener(Object message) {
        LOG.info(String.format("#### -> Consumed message -> %s", message));
    }
}
