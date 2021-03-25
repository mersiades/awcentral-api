package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.VehicleCreator;
import com.mersiades.awccontent.repositories.VehicleCreatorRepository;
import com.mersiades.awccontent.services.VehicleCreatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleCreatorServiceImpl implements VehicleCreatorService {

    private final VehicleCreatorRepository vehicleCreatorRepository;

    public VehicleCreatorServiceImpl(VehicleCreatorRepository vehicleCreatorRepository) {
        this.vehicleCreatorRepository = vehicleCreatorRepository;
    }

    @Override
    public List<VehicleCreator> findAll() {
        return vehicleCreatorRepository.findAll();
    }

    @Override
    public VehicleCreator findById(String id) {
        return vehicleCreatorRepository.findById(id).orElseThrow();
    }

    @Override
    public VehicleCreator save(VehicleCreator vehicleCreator) {
        return vehicleCreatorRepository.save(vehicleCreator);
    }

    @Override
    public List<VehicleCreator> saveAll(List<VehicleCreator> vehicleCreatorList) {
        return vehicleCreatorRepository.saveAll(vehicleCreatorList);
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
