package com.reservei.securityapi.securityapi.exception;

import com.reservei.securityapi.securityapi.exception.dto.ErrorMessageDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> usuarioNaoEncontradoHandler(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorMessageDto> genericHandler(GenericException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<ErrorMessageDto> inactiveAccountExceptionHandler(InactiveAccountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessageDto());
    }
}
