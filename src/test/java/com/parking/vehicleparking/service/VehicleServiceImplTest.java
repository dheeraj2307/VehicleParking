package com.parking.vehicleparking.service;

import com.parking.vehicleparking.entity.Vehicle;
import com.parking.vehicleparking.entity.VehicleType;
import com.parking.vehicleparking.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class VehicleServiceImplTest {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleServiceImpl vehicleService;

    @BeforeEach
    public void setup(){

        Vehicle vehicle = Vehicle.builder().vehicleNum("1234").vehicleType(VehicleType.BIKE.name()).build();
        vehicleRepository.save(vehicle);

    }

    @Test
    public void shouldFetchVehicle(){

        Assertions.assertEquals(1,vehicleService.displayInfo(null).size());
    }

}
