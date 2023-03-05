package org.siit.logisticsystem.controllers;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.exceptions.DestinationException;
import org.siit.logisticsystem.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping("/add")
    public void saveDestination(@ModelAttribute Destination destination) {
        try {
            destinationService.save(destination);
        } catch (DestinationException destinationException) {
            System.out.println(destinationException.getMessage());
        }
    }

    @PutMapping("/update")
    public void updateDestination(@ModelAttribute Destination destination) {
        try {
            destinationService.update(destination);
        } catch (DestinationException destinationException) {
            System.out.println(destinationException.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Destination getDestinationById(@PathVariable Long id) {
        try {
            return destinationService.findById(id).get();
        } catch (DestinationException destinationException) {
            System.out.println(destinationException.getMessage());
        }
        return null;//nu a murit nimeni
    }

    @GetMapping()
    public List<Destination> getAllDestinations() {
        return destinationService.findAll();
    }
    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable Long id){
        try {
            destinationService.delete(id);
        }catch (DestinationException destinationException){
            System.out.println(destinationException.getMessage());
        }
    }
}
