package com.parking.vehicleparking.repository;

import com.parking.vehicleparking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {

    List<Vehicle> findByVehicleType(String vehicleType);

}
