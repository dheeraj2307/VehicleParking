package com.parking.vehicleparking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle")
@Getter @Setter @NoArgsConstructor
public class Vehicle {

    @Id
    @Column()
    private String slotNo;
    @Column
    private String vehicleNum;
    @Column
    private String vehicleType;

    public Vehicle(String vehicleNum,String vehicleType){
        this.vehicleNum = vehicleNum;
        this.vehicleType = vehicleType;
    }

}
