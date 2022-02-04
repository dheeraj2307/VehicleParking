package com.parking.vehicleparking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParkingExceptionAdvisor {
    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<ParkingResponse> handleException(ParkingException exception){
        ParkingResponse parkingResponse = new ParkingResponse();
        parkingResponse.setErrorMessage(exception.getMessage());
        parkingResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(parkingResponse,HttpStatus.NOT_FOUND);
    }
}
