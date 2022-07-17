package com.relay42.sensor.controller.errors.handler;

import com.relay.iot.consumer.simulator.app.controller.errors.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity badRequestException(BadRequestException ex) {
        log.debug("handling exception::" + ex);
        return badRequest().body(ex.getMessage());
    }

}