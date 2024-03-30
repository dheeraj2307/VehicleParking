package com.parking.vehicleparking.service;

import com.parking.vehicleparking.exception.ParkingException;
import com.parking.vehicleparking.entity.Vehicle;
import com.parking.vehicleparking.entity.VehicleType;
import com.parking.vehicleparking.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;
    private final int bikeCapacity = 10;
    private final int carCapacity = 5;

    private int bikeFilled = 0;
    private int carFilled = 0;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle parkVehicle(Vehicle vehicle) {

        bikeFilled = vehicleRepository.findByVehicleType(VehicleType.BIKE.name()).size();
        carFilled = vehicleRepository.findByVehicleType(VehicleType.CAR.name()).size();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> res = restTemplate.getForEntity("https://google.com",String.class);
        log.info("Response: "+res.getBody());

        if (VehicleType.BIKE.name().equals(vehicle.getVehicleType())) {
            if (bikeFilled >= bikeCapacity) {
                throw new ParkingException("Bike parking slots are full.... Please wait");
            }
            bikeFilled++;
        } else if (VehicleType.CAR.name().equals(vehicle.getVehicleType())) {
            if (carFilled >= carCapacity) {
                throw new ParkingException("Car parking slots are full.... Please wait");
            }
            carFilled++;
        }
        else{
            throw new ParkingException("No Vehicle type found...");
        }

        vehicle = buildVehicle(vehicle);
        vehicleRepository.save(vehicle);
        return vehicle;
    }


    @Override
    public Vehicle getVehicle(String slotNo) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(slotNo);
        if (vehicle.isEmpty()) {
            throw new ParkingException("No Vehicle Found with slotNo: " + slotNo);
        }
        return (Vehicle) vehicle.get();
    }

    @Override
    public List<Vehicle> displayInfo(String vehicleType) {

        List<Vehicle> vehicleList;
        if (vehicleType != null) {
            vehicleList = vehicleRepository.findByVehicleType(vehicleType);
        } else {
            vehicleList = vehicleRepository.findAll();
        }
        return vehicleList;
    }

    @Override
    public Vehicle unParking(String slotNo) {
        Vehicle vehicle = getVehicle(slotNo);
        if(vehicle.getVehicleType().equals(VehicleType.BIKE.name())){
            bikeFilled--;
        }
        else{
            carFilled--;
        }
        vehicleRepository.deleteById(slotNo);
        return vehicle;
    }

    private Vehicle buildVehicle(Vehicle vehicle) {
        return vehicle.builder()
                .vehicleNum(vehicle.getVehicleNum())
                .vehicleType(vehicle.getVehicleType())
                .slotNo(VehicleService.generateSlotNo())
                .build();

    }

}
