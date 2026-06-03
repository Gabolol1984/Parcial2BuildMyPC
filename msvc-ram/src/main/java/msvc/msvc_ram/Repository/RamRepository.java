package msvc.msvc_ram.Repository;

import msvc.msvc_ram.Model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RamRepository extends JpaRepository<Ram,Long> {

    List<Ram> findByTipoDdr(String tipoDdr);
    List<Ram> findByFrecuenciaMhz(Integer frecuenciaMhz);
    List<Ram> findByCapacidadGb(Integer capacidadGb);
    List<Ram> findByTipo(String tipo);
    List<Ram> findByEstado(String estado);
    List<Ram> findByMarca(String marca);
    List<Ram> findByCapacidadGbGreaterThanEqual(Integer capacidadMinima);
    Optional<Ram> findByModelo(String modelo);
}