package com.buildmypc.msvc_usuario.exception;


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

    @ExceptionHandler(usuarioException.class)
    public ResponseEntity<?> handlerUsuarioException(usuarioException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un problema al procesar el usuario. Detalle: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
