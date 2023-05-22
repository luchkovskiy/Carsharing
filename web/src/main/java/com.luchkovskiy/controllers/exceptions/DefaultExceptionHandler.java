package com.luchkovskiy.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.luchkovskiy.controllers.response.ApplicationErrorCodes.BAD_REQUEST_ERROR;
import static com.luchkovskiy.controllers.response.ApplicationErrorCodes.FATAL_ERROR;
import static com.luchkovskiy.controllers.response.ApplicationErrorCodes.NOT_FOUND_ERROR;
import static com.luchkovskiy.controllers.response.ApplicationErrorCodes.SQL_ERROR;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<ErrorMessage> handleIllegalRequestException(IllegalRequestException e) {
        String exceptionUniqueId = getUUID();

        BindingResult bindingResult = e.getBindingResult();
        String collect = bindingResult
                .getAllErrors()
                .stream()
                .map(ObjectError::toString)
                .collect(Collectors.joining(","));

        log.error(exceptionUniqueId + e.getMessage(), e);

        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        BAD_REQUEST_ERROR.getCodeId(),
                        collect
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        String exceptionUniqueId = getUUID();
        log.error(exceptionUniqueId + e.getMessage(), e);
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        FATAL_ERROR.getCodeId(),
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e) {
        String exceptionUniqueId = getUUID();
        log.error(exceptionUniqueId + e.getMessage(), e);
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        NOT_FOUND_ERROR.getCodeId(),
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessage> handleSqlException(SQLException e) {
        String exceptionUniqueId = getUUID();
        log.error(exceptionUniqueId + e.getMessage(), e);
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        SQL_ERROR.getCodeId(),
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

}
