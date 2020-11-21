package polytech.covidalert;

import org.junit.Test;
import org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import polytech.covidalert.kafka.KafkaLocation;
import polytech.covidalert.kafka.KafkaLocationsConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationManagementTests {

    @Autowired
    private KafkaLocationsConsumer kafkaLocationsConsumer;

    @Test
    public void addSetOfLocations () throws Exception {
        KafkaLocation location1 = new KafkaLocation("john@gmail.com", 43.16581f, 3.0056f, 1000);
        KafkaLocation location2 = new KafkaLocation("john@gmail.com", 43.16581f, 3.0056f, 2000);
        KafkaLocation location3 = new KafkaLocation("arthur@gmail.com", 43.16581f, 3.0056f, 2000);
        kafkaLocationsConsumer.locationsListener(location1);
        kafkaLocationsConsumer.locationsListener(location2);
        kafkaLocationsConsumer.locationsListener(location3);
        System.out.println(kafkaLocationsConsumer.getLocationsToBeTreated());
        System.out.println(kafkaLocationsConsumer.getSetOfLocations());

    }

}
