package msvc_component.msvc_component.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class Audit {
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }
}