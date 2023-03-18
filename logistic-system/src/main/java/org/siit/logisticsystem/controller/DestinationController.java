package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.exception.DataNotFoundException;
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

    @PutMapping("/destinations/update")
    public void updateDestination(@RequestBody Destination destination) {
        try {
            destinationService.update(destination);
        } catch (DataNotFoundException destinationException) {
            System.out.println(destinationException.getMessage());
        }
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
