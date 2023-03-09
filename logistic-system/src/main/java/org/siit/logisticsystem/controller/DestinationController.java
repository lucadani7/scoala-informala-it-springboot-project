package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.exception.DestinationException;
import org.siit.logisticsystem.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Destination> getDestinationById(@PathVariable Long id) {
        try {
            return destinationService.findById(id);
        } catch (DestinationException destinationException) {
            System.out.println(destinationException.getMessage());
        }
        return Optional.empty();//nu a murit nimeni
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
