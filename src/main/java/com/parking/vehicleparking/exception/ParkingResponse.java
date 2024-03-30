package com.parking.vehicleparking.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ParkingResponse {
    private int statusCode;
    private String errorMessage;
}
