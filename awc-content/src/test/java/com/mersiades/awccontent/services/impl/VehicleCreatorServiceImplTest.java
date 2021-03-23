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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleCreatorServiceImplTest {

    public static final String MOCK_VEHICLE_CREATOR_ID_1 = "mock-vehicle-creator-id-1";

    @Mock
    VehicleCreatorRepository vehicleCreatorRepository;

    VehicleCreatorService vehicleCreatorService;

    VehicleCreator mockVehicleCreator1;

    VehicleCreator mockVehicleCreator2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        vehicleCreatorService = new VehicleCreatorServiceImpl(vehicleCreatorRepository);

        mockVehicleCreator1 = VehicleCreator.builder()
                .id(MOCK_VEHICLE_CREATOR_ID_1).build();

        mockVehicleCreator2 = VehicleCreator.builder().build();
    }

    @Test
    void shouldFindAllVehicleCreators() {
        // Given
        when(vehicleCreatorRepository.findAll()).thenReturn(List.of(mockVehicleCreator1, mockVehicleCreator2));

        // When
        List<VehicleCreator> returnedVehicleCreators = vehicleCreatorService.findAll();

        // Then
        assert returnedVehicleCreators != null;
        assertEquals(2, returnedVehicleCreators.size());
        verify(vehicleCreatorRepository, times(1)).findAll();
    }

    @Test
    void shouldFindVehicleCreatorById() {
        // Given
        when(vehicleCreatorRepository.findById(anyString())).thenReturn(Optional.of(mockVehicleCreator1));

        // When
        VehicleCreator returnedVehicleCreator = vehicleCreatorService.findById(MOCK_VEHICLE_CREATOR_ID_1);

        // Then
        assert returnedVehicleCreator != null;
        assertEquals(MOCK_VEHICLE_CREATOR_ID_1, returnedVehicleCreator.getId());
        verify(vehicleCreatorRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveVehicleCreator() {
        // Given
        when(vehicleCreatorService.save(any(VehicleCreator.class))).thenReturn(mockVehicleCreator1);

        // When
        VehicleCreator savedVehicleCreator = vehicleCreatorService.save(mockVehicleCreator1);

        // Then
        assert savedVehicleCreator != null;
        assertEquals(MOCK_VEHICLE_CREATOR_ID_1, savedVehicleCreator.getId());
        verify(vehicleCreatorRepository, times(1)).save(any(VehicleCreator.class));
    }

    @Test
    void shouldSaveAllVehicleCreators() {
        // Given
        when(vehicleCreatorRepository.saveAll(anyIterable())).thenReturn(List.of(mockVehicleCreator1, mockVehicleCreator2));

        // When
        List<VehicleCreator> savedVehicleCreators = vehicleCreatorService.saveAll(List.of(mockVehicleCreator1, mockVehicleCreator2));

        // Then
        assert savedVehicleCreators != null;
        assertEquals(2, savedVehicleCreators.size());
        verify(vehicleCreatorRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteVehicleCreator() {
        // When
        vehicleCreatorService.delete(mockVehicleCreator1);

        // Then
        verify(vehicleCreatorRepository, times(1)).delete(any(VehicleCreator.class));
    }

    @Test
    void shouldDeleteVehicleCreatorById() {
        // When
        vehicleCreatorService.deleteById(MOCK_VEHICLE_CREATOR_ID_1);

        // Then
        verify(vehicleCreatorRepository, times(1)).deleteById(anyString());
    }
}