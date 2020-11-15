package polytech.covidalert.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaLocationsConsumer {
    Logger LOG = LoggerFactory.getLogger(KafkaLocationsConsumer.class);

    @KafkaListener(topics = "locations", groupId="group_id")
    public void listener(Object message) {
        LOG.info(String.format("##Locations topic -> Consumed message -> %s", message));
        //on fait un traitement avec ça
        //puis on renvoie dans un autre topic le resultat du traitement
    }
}
