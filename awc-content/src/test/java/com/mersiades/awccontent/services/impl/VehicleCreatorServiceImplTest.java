package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.VehicleCreator;
import com.mersiades.awccontent.repositories.VehicleCreatorRepository;
import com.mersiades.awccontent.services.VehicleCreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.VehicleCreatorContent.vehicleCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleCreatorServiceImplTest {

    @Mock
    VehicleCreatorRepository vehicleCreatorRepository;

    VehicleCreatorService vehicleCreatorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        vehicleCreatorService = new VehicleCreatorServiceImpl(vehicleCreatorRepository);

    }

    // There is only one VehicleCreator
    @Test
    void shouldFindAllVehicleCreators() {
        // Given
        when(vehicleCreatorRepository.findAll()).thenReturn(List.of(vehicleCreator));

        // When
        List<VehicleCreator> returnedVehicleCreators = vehicleCreatorService.findAll();

        // Then
        assert returnedVehicleCreators != null;
        assertEquals(1, returnedVehicleCreators.size());
        verify(vehicleCreatorRepository, times(1)).findAll();
    }

    @Test
    void shouldFindVehicleCreatorById() {
        // Given
        when(vehicleCreatorRepository.findById(anyString())).thenReturn(Optional.of(vehicleCreator));

        // When
        VehicleCreator returnedVehicleCreator = vehicleCreatorService.findById(vehicleCreator.getId());

        // Then
        assert returnedVehicleCreator != null;
        assertEquals(vehicleCreator.getId(), returnedVehicleCreator.getId());
        verify(vehicleCreatorRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveVehicleCreator() {
        // Given
        when(vehicleCreatorService.save(any(VehicleCreator.class))).thenReturn(vehicleCreator);

        // When
        VehicleCreator savedVehicleCreator = vehicleCreatorService.save(vehicleCreator);

        // Then
        assert savedVehicleCreator != null;
        assertEquals(vehicleCreator.getId(), savedVehicleCreator.getId());
        verify(vehicleCreatorRepository, times(1)).save(any(VehicleCreator.class));
    }

    // There is only one VehicleCreator
    @Test
    void shouldSaveAllVehicleCreators() {
        // Given
        when(vehicleCreatorRepository.saveAll(anyIterable())).thenReturn(List.of(vehicleCreator));

        // When
        List<VehicleCreator> savedVehicleCreators = vehicleCreatorService.saveAll(List.of(vehicleCreator));

        // Then
        assert savedVehicleCreators != null;
        assertEquals(1, savedVehicleCreators.size());
        verify(vehicleCreatorRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteVehicleCreator() {
        // When
        vehicleCreatorService.delete(vehicleCreator);

        // Then
        verify(vehicleCreatorRepository, times(1)).delete(any(VehicleCreator.class));
    }

    @Test
    void shouldDeleteVehicleCreatorById() {
        // When
        vehicleCreatorService.deleteById(vehicleCreator.getId());

        // Then
        verify(vehicleCreatorRepository, times(1)).deleteById(anyString());
    }
}