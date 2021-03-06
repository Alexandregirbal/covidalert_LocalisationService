package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.kafka.KafkaProducer;
import polytech.covidalert.models.Location;
import polytech.covidalert.models.LocationRepository;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
/**    private final KafkaProducer kafkaProducer;

    @Autowired
    LocationController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public Object sendMessageToKafkaTopic(@RequestBody final Location location) {
        this.kafkaProducer.sendMessage(location,"locations");
        return location;
    }*/
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<Location> listLocation() {
        return locationRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Location get(@PathVariable Long id) {
        if (! locationRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with ID " +id+ " not found.");
        }
        return locationRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Location create(@RequestBody final Location location) {
        return locationRepository.saveAndFlush(location);
    }
}
