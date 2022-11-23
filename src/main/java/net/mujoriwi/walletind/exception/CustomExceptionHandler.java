package net.mujoriwi.walletind.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.mujoriwi.walletind.exception.custom.CustomBadRequestException;
import net.mujoriwi.walletind.exception.custom.CustomNotFoundException;
import net.mujoriwi.walletind.exception.custom.CustomUnauthorizedException;
import net.mujoriwi.walletind.model.dto.response.ErrorMessage;

@ControllerAdvice
public class CustomExceptionHandler {
    private ErrorMessage<Object> errorMessage;
    private Map<Object, Object> errors;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleExceptionGlobal(Exception e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),
                e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(value = CustomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomNotFoundException e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(),
                e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(value = CustomBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(CustomBadRequestException e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(value = CustomUnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(CustomUnauthorizedException e) {
        errorMessage = new ErrorMessage<Object>(HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(),
                e.getMessage(), null);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        errors = new HashMap<>();

        e.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        errorMessage = new ErrorMessage<Object>(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                "Error Validation", errors);
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
}
