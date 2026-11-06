package msvc.gpu.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        for(FieldError e: ex.getBindingResult().getFieldErrors()){
            errors.put(e.getField(),        "Campo inválido: '" + e.getField() + "'. " + e.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(GpuException.class)
    public ResponseEntity<?> handleGpuException(GpuException ex){
        Map<String,String> error = new HashMap<>();
        error.put("error",       "Error en el microservicio GPU. Detalle: " + ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}