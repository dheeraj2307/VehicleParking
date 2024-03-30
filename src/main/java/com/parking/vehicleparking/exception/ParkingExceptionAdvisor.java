package com.parking.vehicleparking.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class ParkingExceptionAdvisor {

    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<ParkingResponse> handleException(ParkingException exception){
        log.error("An exception occurred: "+exception.getMessage(),exception);
        ParkingResponse parkingResponse = new ParkingResponse();
        parkingResponse.setErrorMessage(exception.getMessage());
        parkingResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(parkingResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("An exception occurred: "+ex.getMessage(),ex);
        ParkingResponse parkingResponse = new ParkingResponse();
        return new ResponseEntity<>(parkingResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
