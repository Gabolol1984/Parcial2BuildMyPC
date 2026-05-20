package msvc.gpu.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "gpu")
public class Gpu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "componente_id")
    private Long componenteId;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull @Min(1)
    @Column(name = "precio_base")
    private Double precioBase;

    @NotBlank(message = "El tipo de memoria es obligatorio")
    private String tipoMemoria;

    @NotNull @Min(1)
    private Integer memoriaGb;

    @NotNull @Min(1)
    private Integer consumoW;

    private String descripcion;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @Embedded
    private Audit audit = new Audit();
}