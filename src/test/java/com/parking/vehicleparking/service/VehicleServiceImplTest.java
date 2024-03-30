package com.parking.vehicleparking.service;

import com.parking.vehicleparking.entity.Vehicle;
import com.parking.vehicleparking.entity.VehicleType;
import com.parking.vehicleparking.exception.ParkingException;
import com.parking.vehicleparking.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleServiceImpl(vehicleRepository);
    }

    @Test
    void parkVehicle_Success() {
        Vehicle vehicle = new Vehicle("ABC123",  "1", VehicleType.BIKE.name());
        when(vehicleRepository.findByVehicleType(VehicleType.BIKE.name())).thenReturn(Arrays.asList(new Vehicle(), new Vehicle()));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        assertNotNull(vehicleService.parkVehicle(vehicle));
        assertEquals(3, vehicleService.getBikeFilled());
    }

    @Test
    void parkVehicle_Failure_BikeCapacityFull() {
        Vehicle vehicle = new Vehicle("ABC123", VehicleType.BIKE.name(), "1");
        when(vehicleRepository.findByVehicleType(VehicleType.BIKE.name())).thenReturn(Arrays.asList(new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle()));
        assertThrows(ParkingException.class, () -> vehicleService.parkVehicle(vehicle));
    }

    @Test
    void parkVehicle_Failure_CarCapacityFull() {
        Vehicle vehicle = new Vehicle("ABC123", VehicleType.CAR.name(), "1");
        when(vehicleRepository.findByVehicleType(VehicleType.CAR.name())).thenReturn(Arrays.asList(new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle(), new Vehicle()));
        assertThrows(ParkingException.class, () -> vehicleService.parkVehicle(vehicle));
    }

    @Test
    void getVehicle_Success() {
        Vehicle vehicle = new Vehicle("ABC123", VehicleType.BIKE.name(), "1");
        when(vehicleRepository.findById("1")).thenReturn(Optional.of(vehicle));
        assertNotNull(vehicleService.getVehicle("1"));
    }

    @Test
    void getVehicle_Failure() {
        when(vehicleRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ParkingException.class, () -> vehicleService.getVehicle("1"));
    }

    @Test
    void displayInfo_All() {
        List<Vehicle> vehicles = Arrays.asList(new Vehicle(), new Vehicle());
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        assertEquals(2, vehicleService.displayInfo(null).size());
    }

    @Test
    void displayInfo_ByVehicleType() {
        List<Vehicle> vehicles = Arrays.asList(new Vehicle(), new Vehicle());
        when(vehicleRepository.findByVehicleType("BIKE")).thenReturn(vehicles);
        assertEquals(2, vehicleService.displayInfo("BIKE").size());
    }

    @Test
    void unParking_Success() {
        Vehicle vehicle = new Vehicle("ABC123",  "1", VehicleType.CAR.name());
        when(vehicleRepository.findByVehicleType("CAR")).thenReturn(List.of(vehicle));
        when(vehicleRepository.findByVehicleType("BIKE")).thenReturn(new ArrayList<>());
        when(vehicleRepository.findById("1")).thenReturn(Optional.of(vehicle));
        assertEquals(vehicle, vehicleService.unParking("1"));
    }

    @Test
    void unParking_Bike() {
        Vehicle vehicle = new Vehicle("ABC123",  "1", VehicleType.BIKE.name());
        when(vehicleRepository.findByVehicleType("CAR")).thenReturn(new ArrayList<>());
        when(vehicleRepository.findByVehicleType("BIKE")).thenReturn(List.of(vehicle));
        when(vehicleRepository.findById("1")).thenReturn(Optional.of(vehicle));
        vehicleService.unParking("1");
        assertEquals(0, vehicleService.getBikeFilled());
    }

    @Test
    void unParking_Car() {
        Vehicle vehicle = new Vehicle("ABC123",  "1", VehicleType.CAR.name());
        when(vehicleRepository.findByVehicleType(VehicleType.CAR.name())).thenReturn(List.of(vehicle));
        when(vehicleRepository.findByVehicleType("BIKE")).thenReturn(new ArrayList<>());
        when(vehicleRepository.findById("1")).thenReturn(Optional.of(vehicle));
        vehicleService.unParking("1");
        assertEquals(0, vehicleService.getCarFilled());
    }

}
