package msvc.cpu.Repository;

import msvc.cpu.model.cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cpuRepository extends JpaRepository<cpu, Long> {
    List<cpu> findBySocket(String socket);
    Optional<cpu> findById(Long id);
    List<cpu> findByGeneracion(String generacion);
    List<cpu> id(Long id);
}
