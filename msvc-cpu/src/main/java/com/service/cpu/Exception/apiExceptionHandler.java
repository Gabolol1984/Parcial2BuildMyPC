package com.service.cpu.Exception;



import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class apiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError e : ex.getBindingResult().getFieldErrors()) {
            errors.put(e.getField(), e.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
//mensajes más descriptivos para los errores
    @ExceptionHandler(cpuException.class)
    public ResponseEntity<?> handleCpuException(cpuException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un problema al procesar la CPU. Detalle: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}