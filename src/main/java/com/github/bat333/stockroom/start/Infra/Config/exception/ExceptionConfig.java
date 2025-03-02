package com.github.bat333.stockroom.start.Infra.Config.exception;

import com.github.bat333.stockroom.start.Application.Exception.PartExists;
import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Domain.Exception.PartValidation;
import com.github.bat333.stockroom.start.Domain.Exception.SectorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler({PartValidation.class, SectorValidation.class})
    public ResponseEntity<Object> validationException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getClass().getSimpleName());
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler({PartExists.class, SectorExists.class})
    public ResponseEntity<Object> existsException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getClass().getSimpleName());
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity argument(MethodArgumentNotValidException ex){
        var erro =  ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erro.stream().map(MessageError::new).toList());

    }
    public record MessageError(String field, String description){
        public MessageError(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }

    }
}
