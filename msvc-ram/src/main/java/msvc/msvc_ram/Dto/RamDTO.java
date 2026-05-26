package msvc.msvc_ram.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RamDTO {
    private Long id;

    @NotBlank(message = "El tipo de componente no puede ser vacio")
    private String tipo;

    @NotBlank(message = "La marca no puede ser vacia")
    private String marca;

    @NotBlank(message = "El modelo no puede ser vacio")
    private String modelo;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser mayor que cero")
    private Double precioBase;

    private String estado;
    private String descripcion;
    private LocalDate fechaLanzamiento;


    @NotBlank(message = "El tipo DDR es obligatorio")
    private String tipoDdr;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor que cero")
    private Integer capacidadGb;

    @NotNull(message = "La frecuencia es obligatoria")
    @Positive(message = "La frecuencia debe ser mayor que cero")
    private Integer frecuenciaMhz;

    @NotBlank(message = "La latencia CL es obligatoria")
    private String latenciaCl;

    @NotNull(message = "La cantidad de modulos es obligatoria")
    @Positive(message = "La cantidad de modulos debe ser mayor que cero")
    private Integer modulos;

    @NotNull(message = "El voltaje es obligatorio")
    @Positive(message = "El voltaje debe ser mayor que cero")
    private Double voltaje;


    @NotNull(message = "Debe indicar si esta activo")
    private Boolean activo;
}