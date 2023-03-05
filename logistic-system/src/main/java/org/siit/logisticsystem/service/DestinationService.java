package org.siit.logisticsystem.service;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.exceptions.DestinationException;
import org.siit.logisticsystem.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;

    public void save(Destination destination) {

        if (destinationRepository.existsById(destination.getId())) {
            throw new DestinationException("Destination already exist");
        } else {
            destinationRepository.save(destination);
        }
    }

    public void update(Destination destination) {
        if (destinationRepository.existsById(destination.getId())) {
            destinationRepository.save(destination);
        } else {
            throw new DestinationException("ID invalid at update");
        }
    }

    public void delete(Long id) {
        if (destinationRepository.existsById(id)) {
            destinationRepository.deleteById(id);
        } else {
            throw new DestinationException("Id doesn't exist ");
        }
    }
    public Optional<Destination> findById(Long id){
        if (destinationRepository.existsById(id)) {
            return destinationRepository.findById(id);
        } else {
            throw new DestinationException("Id doesn't exist ");
        }
    }

    public List<Destination> findAll(){
        return destinationRepository.findAll();
    }

}
