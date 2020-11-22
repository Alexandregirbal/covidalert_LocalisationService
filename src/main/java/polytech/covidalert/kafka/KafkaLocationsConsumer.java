package polytech.covidalert.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import polytech.covidalert.controllers.CovidDeclarationController;

import java.util.ArrayList;

//DO NOT FORGET:
//   bin/zookeeper-server-start.sh config/zookeeper.properties
//   bin/kafka-server-start.sh config/server.properties
@Component
public class KafkaLocationsConsumer {
    Logger LOG = LoggerFactory.getLogger(KafkaLocationsConsumer.class);

    private int minuteOfTimestamp;
    private ArrayList<KafkaLocation> setOfLocations;
    private ArrayList<ArrayList<KafkaLocation>> locationsToBeTreated;
    private int numberOfMinutesToBeClose;

    public KafkaLocationsConsumer() {
        this.minuteOfTimestamp = 0;
        this.numberOfMinutesToBeClose = 3;
        this.setOfLocations = new ArrayList<KafkaLocation>();
        this.setOfLocations = new ArrayList<KafkaLocation>();
        this.locationsToBeTreated = new ArrayList<ArrayList<KafkaLocation>>();
        for (int i = 0; i < numberOfMinutesToBeClose; i++) {
            this.locationsToBeTreated.add(new ArrayList<KafkaLocation>());
        }
    }

    public void addSetOfLocations() {
        try {
            for(int i = numberOfMinutesToBeClose-1; i >= 1; i--){
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
        for(int i = 0; i<numberOfMinutesToBeClose; i++) {
            if (locationsToBeTreated.get(i).isEmpty()){
                result = true;
            }
        }
        return result;
    }

    private boolean locationsAreCloserThanDistance(int distance, KafkaLocation l1, KafkaLocation l2) {
        float lat1 = l1.getLatitude();
        float long1 = l1.getLongitude();
        float lat2 = l2.getLatitude();
        float long2 = l2.getLongitude();
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

    private ArrayList<KafkaPairOfCloseUsers> deleteDoublons(ArrayList<KafkaPairOfCloseUsers> closeUsers) {
        ArrayList<KafkaPairOfCloseUsers> simpleCloseUsers = (ArrayList<KafkaPairOfCloseUsers>) closeUsers.clone();
        for (int i = 0; i < closeUsers.size(); i++) {
            for (int j = i + 1; j < closeUsers.size(); j++) {
                if (KafkaPairOfCloseUsers.areTwoPairsSimilars(closeUsers.get(i), closeUsers.get(j))) {
                    simpleCloseUsers.remove(j);
                }
            }

        }
        return simpleCloseUsers;
    }

    private ArrayList<KafkaPairOfCloseUsers> generateCloseUsers(ArrayList<KafkaLocation> l1, ArrayList<KafkaLocation> l2) {
        ArrayList<KafkaPairOfCloseUsers> closeUsers = new ArrayList<KafkaPairOfCloseUsers>();

        for (int i = 0; i < l1.size(); i++){
            for (int j = 0; j < l2.size(); j++){
                KafkaLocation location1 = l1.get(i);
                KafkaLocation location2 = l2.get(j);
                if (!location1.getUserEmail().equals(location2.getUserEmail())) {
                    if (locationsAreCloserThanDistance(20, location1, location2)) {

                        closeUsers.add(new KafkaPairOfCloseUsers(location1.getUserEmail(), location2.getUserEmail(), location1.getTimestamp()));
                    }
                }
            }
        }
        return deleteDoublons(closeUsers);
    }

    private ArrayList<KafkaPairOfCloseUsers> getCommonCloseUsers(ArrayList<KafkaPairOfCloseUsers> closeUsers1, ArrayList<KafkaPairOfCloseUsers> closeUsers2) {
        ArrayList<KafkaPairOfCloseUsers> closeUsers = new ArrayList<KafkaPairOfCloseUsers>();
        for (int i = 0; i < closeUsers1.size(); i++) {
            for (int j = 0; j < closeUsers2.size(); j++) {
                //compare les 2 emails un a un
                if (KafkaPairOfCloseUsers.areTwoPairsSimilars(closeUsers1.get(i), closeUsers2.get(j))){
                    closeUsers.add(closeUsers1.get(i));
                }
            }
        }
        return closeUsers;
    }


    @KafkaListener(topics = "locations", groupId="15")
    public void locationsListener(@Payload KafkaLocation message) {
        LOG.info(String.format("##### Locations topic -> Consumed message -> %s / %s / %s / %s", message.getLatitude(), message.getLongitude(), message.getUserEmail(), message.getTimestamp()));
        int minute = message.getTimestamp()/60;
        if (minuteOfTimestamp != minute){ //minute has changed
            minuteOfTimestamp = minute;
            handleLocations();
        }
        setOfLocations.add(message);
    }

    public void handleLocations() {
        addSetOfLocations();
        if(!locationsToBeTreatedContainsEmptyMinutes()){
            System.out.println("Compare locations and save closeUsers");
            Boolean invariant = true;
            int index = 1;
            ArrayList<KafkaPairOfCloseUsers> closeUsers = generateCloseUsers(locationsToBeTreated.get(numberOfMinutesToBeClose-1), locationsToBeTreated.get(0));

            while ( invariant && index <= numberOfMinutesToBeClose-2 ) {
                ArrayList<KafkaPairOfCloseUsers> nextCloseUsers = generateCloseUsers(locationsToBeTreated.get(numberOfMinutesToBeClose-1), locationsToBeTreated.get(index));
                closeUsers = getCommonCloseUsers(closeUsers, nextCloseUsers);
                if (closeUsers.isEmpty()) {
                    invariant = false;
                }
                index += 1;
            }
            System.out.println(index + ": " + closeUsers);
            if (!closeUsers.isEmpty()) {
                //on met en base ici
                CovidDeclarationController.addPairsOfCloseUsers(closeUsers);
            }
        } else {
            //System.out.println("At least one location set is empty, wait to get more.");
        }
    }

    public int getMinuteOfTimestamp() {
        return minuteOfTimestamp;
    }

    public ArrayList<KafkaLocation> getSetOfLocations() {
        return setOfLocations;
    }

    public ArrayList<ArrayList<KafkaLocation>> getLocationsToBeTreated() {
        return locationsToBeTreated;
    }

}
