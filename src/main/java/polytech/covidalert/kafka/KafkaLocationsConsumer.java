package polytech.covidalert.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class KafkaLocationsConsumer {
    Logger LOG = LoggerFactory.getLogger(KafkaLocationsConsumer.class);

    private int minuteOfTimestamp;
    private ArrayList<KafkaLocation> setOfLocations;
    private ArrayList<ArrayList<KafkaLocation>> locationsToBeTreated;
    private ArrayList<KafkaCloseUsersOfUser> usersRelations;

    public KafkaLocationsConsumer() {
        this.minuteOfTimestamp = 0;
        this.setOfLocations = new ArrayList<KafkaLocation>();
        this.locationsToBeTreated = new ArrayList<ArrayList<KafkaLocation>>();
        this.usersRelations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.locationsToBeTreated.add(new ArrayList<KafkaLocation>());
        }
    }

    public void addSetOfLocations() {
        try {
            for(int i = 5-1; i >= 1; i--){
                locationsToBeTreated.set(i, (ArrayList<KafkaLocation>) locationsToBeTreated.get(i-1).clone());
            }
            locationsToBeTreated.set(0, (ArrayList<KafkaLocation>) setOfLocations.clone());
            setOfLocations.clear();
        } catch (Exception e) {
            System.out.println("Error while adding setOfLocations in locationsToBeTreated:");
        }
    }

    public Boolean locationsToBeTreatedContainsEmptyMinutes() {
        Boolean result = false;
        for(int i = 0; i<5; i++) {
            if (locationsToBeTreated.get(i).isEmpty()){
                result = true;
            }
        }
        return result;
    }

    public boolean locationsAreCloserThanDistance(int distance, float lat1, float long1, float lat2, float long2) {
        if ((lat1 == lat2) & (long1 == long2)) {
            return true;
        }
        else {
            var radLat1 = Math.PI * lat1 / 180;
            var radLat2 = Math.PI * lat2 / 180;
            var theta = long1 - long2;
            var radTheta = Math.PI * theta / 180;
            var dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
            if (dist > 1) {
                dist = 1;
            }
            dist = Math.acos(dist);
            dist = dist * 180 / Math.PI;
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344 * 1000;
            return dist <= distance;
        }
    }


    @KafkaListener(topics = "locations", groupId="15")
    public void locationsListener(@Payload KafkaLocation message) {
        LOG.info(String.format("##### Locations topic -> Consumed message -> %s / %s / %s / %s", message.getLatitude(), message.getLongitude(), message.getUserEmail(), message.getTimestamp()));
        int minute = message.getTimestamp()/60;
        if (minuteOfTimestamp != minute){ //minute has changed
            minuteOfTimestamp = minute;
            addSetOfLocations();
            if(!locationsToBeTreatedContainsEmptyMinutes()){
                System.out.println("Compare locations and send to next topic");


            } else {
                System.out.println("At least one location set is empty, wait to get more.");
            }
        }
        setOfLocations.add(message);
        System.out.println(setOfLocations);
    }
}
