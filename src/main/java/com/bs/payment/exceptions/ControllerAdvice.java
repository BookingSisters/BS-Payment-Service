package com.bs.payment.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bs.payment.dtos.response.ErrorResultResponseDto;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResultResponseDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] ex", e);
        return new ErrorResultResponseDto(HttpStatus.BAD_REQUEST.toString(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResultResponseDto resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        log.error("[ResourceNotFoundException] ex", e);
        return new ErrorResultResponseDto("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResultResponseDto illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResultResponseDto("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResultResponseDto exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e.getMessage());
        return new ErrorResultResponseDto("500", e.getMessage());
    }
}
