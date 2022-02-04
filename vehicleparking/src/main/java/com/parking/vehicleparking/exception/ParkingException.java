package com.parking.vehicleparking.exception;



public class ParkingException extends RuntimeException{

    private String message;
    public ParkingException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
