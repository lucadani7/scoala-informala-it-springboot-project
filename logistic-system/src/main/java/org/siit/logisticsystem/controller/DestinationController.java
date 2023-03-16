package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping("/destinations/add")
    public void saveDestination(@RequestBody Destination destination) {
        destinationService.saveDestination(destination);
    }

    @PutMapping("/destinations/update/{id}")
    public Destination updateDestination(@RequestBody Destination destination, @PathVariable Long id) {
        return destinationService.update(destination, id);
    }

    @GetMapping("/destinations/{id}")
    public Destination getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id);
    }

    @GetMapping("/destinations")
    public List<Destination> getAllDestinations() {
        return destinationService.findAll();
    }

    @DeleteMapping("/destinations/{id}")
    public void deleteDestination(@PathVariable Long id){
        destinationService.deleteDestination(id);
    }
}
