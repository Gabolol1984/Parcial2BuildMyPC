package msvc.powersupply.Repository;


import msvc.powersupply.Model.powerSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface psuRepository extends JpaRepository<powerSupply,Long> {

    Optional<powerSupply> findById(Long id);
    powerSupply save(powerSupply powerSupply);
    Optional<powerSupply> findByComponenteId(Long componenteId);
}
