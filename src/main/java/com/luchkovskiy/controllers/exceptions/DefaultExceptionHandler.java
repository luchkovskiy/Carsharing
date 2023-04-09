package com.luchkovskiy.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.UUID;

import static com.luchkovskiy.controllers.response.ApplicationErrorCodes.*;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        String exceptionUniqueId = UUID.randomUUID().toString();
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
        String exceptionUniqueId = UUID.randomUUID().toString();
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
        String exceptionUniqueId = UUID.randomUUID().toString();
        log.error(exceptionUniqueId + e.getMessage(), e);
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        SQL_ERROR.getCodeId(),
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
