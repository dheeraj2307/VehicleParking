package com.parking.vehicleparking.controller;


import com.parking.vehicleparking.entity.Vehicle;
import com.parking.vehicleparking.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class ParkingController {


    private VehicleService vehicleService;

    @Autowired
    public ParkingController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    public String welcomeMessage(){
        return "Welcome to our parking area \n1. allinfo: vehicle/allinfo?vehicletype=CAR" +
                "\n2. parking: vehicle/parking/{json}" +
                "\n3. info: vehicle/info/slotno" +
                "\n4. unparking: vehicle/unparking/slotno";
    }

    @GetMapping("/allinfo")
    public List<Vehicle> findAll(@RequestParam(required = false) String vehicleType){
        return vehicleService.displayInfo(vehicleType);
    }

    @PostMapping("/parking")
    public Vehicle parkVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.parkVehicle(vehicle);
    }

    @GetMapping("/info/{slotNo}")
    public Vehicle findBySlotNo(@PathVariable String slotNo){
        return vehicleService.getVehicle(slotNo);
    }
    @DeleteMapping("/unparking/{slotNo}")
    public Vehicle unParking(@PathVariable String slotNo){
        return vehicleService.unParking(slotNo);
    }

}
