package com.example.robofinance.advices;

import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class CustomerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String customerNotFoundHandler(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
