package com.github.bat333.stockroom.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Exceptions {
    @ExceptionHandler({StockExceptions.class})
    public ResponseEntity<Object> handleStockException(StockExceptions ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Stock Error");
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity argument(MethodArgumentNotValidException ex){
        var erros =  ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(MensagemErros::new).toList());

    }
    public record MensagemErros(String field, String description){
        public MensagemErros(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }

    }
}
