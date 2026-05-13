package msvc_component.msvc_component.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;


@Entity
    @Table(name = "componentes")
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public class Componente {
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

    @Embedded
    private Audit audit = new Audit();
}
