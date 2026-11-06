package msvc.powersupply.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="powerSupply")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class powerSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psu_id")
    private Long id;

    // Referencia al catálogo general (component-service)
    @Column(nullable = false)
    private Long componenteId;

    @Column(nullable = false)
    private String componenteName;

    @NotNull(message = "El campo de potencia en watts no puede estar vacio")
    @Column(nullable = false)
    private Integer potenciaWatts;

    @NotBlank(message = "El campo de certificacion no puede estar vacio")
    @Column(nullable = false, length = 10)
    private String certificacion;     // Ej: 80+ Bronze, Gold, Platinum

    @Column(nullable = false)
    private Boolean modular;          // true = modular, false = no modular

    @NotNull(message = "El campo de conectores no puede estar vacio")
    @Column(nullable = false)
    private Integer conectoresPcie;

    @NotNull(message = "El campo de valor no puede estar vacio")
    @Column(nullable = false)
    private Integer valor;

}
