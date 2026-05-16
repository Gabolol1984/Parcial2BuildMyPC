package msvc.msvc_ram.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ram")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "componente_id")
    private Long componenteId;

    @NotBlank(message = "El tipo de componente no puede ser vacio")
    private String tipo;

    @NotBlank(message = "La marca no puede ser vacia")
    private String marca;

    @NotBlank(message = "El modelo no puede ser vacio")
    private String modelo;

    @NotNull(message = "El precio base es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor que cero")
    @Column(name = "precio_base")
    private Double precioBase;

    private String estado;
    private String descripcion;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    // ===== Campos técnicos RAM =====
    @NotBlank(message = "El tipo DDR es obligatorio")
    private String tipoDdr;

    @NotNull(message = "La capacidad es obligatoria")
    private Integer capacidadGb;

    @NotNull(message = "La frecuencia es obligatoria")
    private Integer frecuenciaMhz;

    @NotBlank(message = "La latencia CL es obligatoria")
    private String latenciaCl;

    @NotNull(message = "La cantidad de módulos es obligatoria")
    @Min(1)
    private Integer modulos;

    @NotNull(message = "El voltaje es obligatorio")
    @Min(1)
    private Double voltaje;

    @NotNull(message = "Debe indicar si está activo")
    private Boolean activo;

    // ===== Audit =====
    @Embedded
    private Audit audit = new Audit();
}