package msvc.msvc_ram.Repository;

import msvc.msvc_ram.Model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RamRepository extends JpaRepository<Ram,Long> {

    List<Ram> findByTipoDdr(String tipoDdr);
    List<Ram> findByFrecuenciaMhz(Integer frecuenciaMhz);
    List<Ram> findByCapacidadGb(Integer capacidadGb);
}