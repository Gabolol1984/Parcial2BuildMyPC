package msvc_component.msvc_component.Repository;

import msvc_component.msvc_component.Model.Componente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long> {
    Optional<Componente> findByTipo(String tipo);
    List<Componente> findByMarca(String marca);
}
