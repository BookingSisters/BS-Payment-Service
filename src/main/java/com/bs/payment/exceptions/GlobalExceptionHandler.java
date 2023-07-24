package com.bs.payment.exceptions;

import com.bs.payment.dtos.response.ErrorResultResponseDto;
import com.bs.payment.exceptions.badReqeust.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResultResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] e", e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public ErrorResultResponseDto handleInvalidValueException(InvalidValueException e) {
        log.error("[InvalidValueException] e", e.getClass().getSimpleName(), e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResultResponseDto handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] e", e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResultResponseDto exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e.getMessage());
        return new ErrorResultResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
    }
}
