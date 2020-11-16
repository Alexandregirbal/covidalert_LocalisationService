package polytech.covidalert.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import polytech.covidalert.models.Location;

@Component
public class KafkaLocationsConsumer {
    Logger LOG = LoggerFactory.getLogger(KafkaLocationsConsumer.class);

    @KafkaListener(topics = "locations", groupId="15")
    public void locationsListener(KafkaLocation message) {
        LOG.info(String.format("##### Locations topic -> Consumed message -> %s / %s / %s", message.getLatitude(), message.getLongitude(), message.getUserEmail()));
        //on fait un traitement avec ça
        //puis on renvoie dans un autre topic le resultat du traitement
    }
}
