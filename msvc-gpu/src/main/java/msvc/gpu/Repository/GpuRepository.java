package msvc.gpu.Repository;

import msvc.gpu.Model.Gpu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuRepository extends JpaRepository<Gpu, Long> {
}