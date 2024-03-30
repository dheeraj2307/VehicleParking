package com.parking.vehicleparking.service;


import com.parking.vehicleparking.entity.Vehicle;
import java.util.List;


public interface VehicleService{
    Vehicle parkVehicle(Vehicle vehicle);
    Vehicle getVehicle(String slotNo);
    List<Vehicle> displayInfo(String vehicleType);
    Vehicle unParking(String slotNo);

    static String generateSlotNo(){

        String id = String.valueOf((int)(Math.random()*900000)+100000);
        return id;
    }

}
