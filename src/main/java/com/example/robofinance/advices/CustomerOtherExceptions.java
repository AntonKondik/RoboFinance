package com.example.robofinance.advices;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class CustomerOtherExceptions {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String customerOtherExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
