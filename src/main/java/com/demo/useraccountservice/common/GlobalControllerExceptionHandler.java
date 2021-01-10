package com.demo.useraccountservice.common;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({EntityAlreadyExists.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<BaseResponse> handleEntityAlreadyExists(EntityAlreadyExists ex) {
        var resp = new BaseResponse();
        resp.setOk(false);
        resp.setError(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleInvalidArgumentException(RuntimeException ex) {
        var resp = new BaseResponse();
        resp.setOk(false);
        resp.setError(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
