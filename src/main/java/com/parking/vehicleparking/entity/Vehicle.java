package com.parking.vehicleparking.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name="vehicle")
public class Vehicle {

    @Id
    @Column(name = "slot_no")
    private String slotNo;
    @Column(name = "vehicle_num")
    private String vehicleNum;
    @Column(name = "vehicle_type")
    private String vehicleType;

}
