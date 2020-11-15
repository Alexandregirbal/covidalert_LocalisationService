package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.kafka.KafkaProducer;
import polytech.covidalert.models.Location;
import polytech.covidalert.models.UserLocation;
import polytech.covidalert.models.UserLocationRepository;

import java.util.List;

@RestController
@RequestMapping("/user-locations")
public class UserLocationController {
    private final KafkaProducer kafkaProducer;

    @Autowired
    UserLocationController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public Object sendMessageToKafkaTopic(@RequestBody final Object userLocation) {
        this.kafkaProducer.sendMessage(userLocation,"mytopic-1");
        System.out.println(userLocation);
        return userLocation;
    }

    @Autowired
    private UserLocationRepository userLocationRepository;

    @GetMapping
    public List<UserLocation> listUserLocation() {
        return userLocationRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public UserLocation get(@PathVariable Long id) {
        if (! userLocationRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserLocation with ID " +id+ " not found.");
        }
        return userLocationRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserLocation create(@RequestBody final UserLocation userLocation) {
        return userLocationRepository.saveAndFlush(userLocation);
    }


    @PostMapping
    @RequestMapping("/send")
    public void send(@RequestBody final UserLocation userLocation) {
        System.out.println(userLocation.toString());

    }
}
