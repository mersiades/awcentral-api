package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.VehicleCreator;
import com.mersiades.awccontent.repositories.VehicleCreatorRepository;
import com.mersiades.awccontent.services.VehicleCreatorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VehicleCreatorServiceImpl implements VehicleCreatorService {

    private final VehicleCreatorRepository vehicleCreatorRepository;

    public VehicleCreatorServiceImpl(VehicleCreatorRepository vehicleCreatorRepository) {
        this.vehicleCreatorRepository = vehicleCreatorRepository;
    }

    @Override
    public Flux<VehicleCreator> findAll() {
        return vehicleCreatorRepository.findAll();
    }

    @Override
    public Mono<VehicleCreator> findById(String id) {
        return vehicleCreatorRepository.findById(id);
    }

    @Override
    public Mono<VehicleCreator> save(VehicleCreator vehicleCreator) {
        return vehicleCreatorRepository.save(vehicleCreator);
    }

    @Override
    public Flux<VehicleCreator> saveAll(Flux<VehicleCreator> vehicleCreatorFlux) {
        return vehicleCreatorRepository.saveAll(vehicleCreatorFlux);
    }

    @Override
    public void delete(VehicleCreator vehicleCreator) {
        vehicleCreatorRepository.delete(vehicleCreator);
    }

    @Override
    public void deleteById(String id) {
        vehicleCreatorRepository.deleteById(id);
    }
}
