package org.siit.logisticsystem.service;

import jakarta.transaction.Transactional;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.exception.DataNotFoundException;
import org.siit.logisticsystem.exception.DuplicatesNotAllowedException;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DestinationService {

    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public void saveDestination(Destination destination) {
        if (destinationRepository.existsById(destination.getId())) {
            throw new DuplicatesNotAllowedException("The destination already exists!");
        }
        destinationRepository.save(destination);
    }

    public Destination update(Destination newDestination, Long id) {
        return destinationRepository.findById(id)
                .map(destination -> {
                    destination.setName(destination.getName());
                    destination.setDistance(destination.getDistance());
                    return destinationRepository.save(destination);
                })
                .orElseGet(() -> {
                    newDestination.setId(newDestination.getId());
                    return destinationRepository.save(newDestination);
                });
    }

    public void deleteDestination(Long id) {
        if (!destinationRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("The destination with id %s does not exist!", id));
        }
        destinationRepository.deleteById(id);
    }

    public Destination getDestinationById(Long id){
        String message = String.format("The destination with id %s does not exist!", id);
        return destinationRepository.findById(id).orElseThrow(() -> new DataNotFoundException(message));
    }

    public List<Destination> findAll(){
        return destinationRepository.findAll();
    }

}
