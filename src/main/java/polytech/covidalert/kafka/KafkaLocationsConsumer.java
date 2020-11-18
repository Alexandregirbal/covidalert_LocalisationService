package polytech.covidalert.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import polytech.covidalert.models.Location;

import java.util.ArrayList;

@Component
public class KafkaLocationsConsumer {
    Logger LOG = LoggerFactory.getLogger(KafkaLocationsConsumer.class);

    private int minuteOfTimestamp;
    private ArrayList<KafkaLocation> setOfLocations;
    private ArrayList<ArrayList<KafkaLocation>> locationsToBeTreated;

    public KafkaLocationsConsumer() {
        this.minuteOfTimestamp = 0;
        this.setOfLocations = new ArrayList<KafkaLocation>();
        this.locationsToBeTreated = new ArrayList<ArrayList<KafkaLocation>>();
        for (int i = 0; i < 5; i++) {
            this.locationsToBeTreated.add(new ArrayList<KafkaLocation>());
        }
    }

    public void addSetOfLocations() {
        try {
            System.out.println(locationsToBeTreated.toString());
            for(int i = 5-1; i >= 1; i--){
                System.out.println(i);
                locationsToBeTreated.set(i, locationsToBeTreated.get(i-1));
            }
            locationsToBeTreated.set(0, setOfLocations);
            setOfLocations.clear();
        } catch (Exception e) {
            System.out.println("Error while adding setOfLocations in locationsToBeTreated:");
        }
    }

    @KafkaListener(topics = "locations", groupId="15")
    public void locationsListener(@Payload KafkaLocation message) {
        LOG.info(String.format("##### Locations topic -> Consumed message -> %s / %s / %s / %s", message.getLatitude(), message.getLongitude(), message.getUserEmail(), message.getTimestamp()));
        System.out.println(String.format("minute: %d",message.getTimestamp()/60));
        int minute = message.getTimestamp()/60;
        if (minuteOfTimestamp != minute){ //minute has changed
            minuteOfTimestamp = minute;
            addSetOfLocations();
        }
        System.out.println(setOfLocations.isEmpty());
        setOfLocations.add(message);
    }
}
