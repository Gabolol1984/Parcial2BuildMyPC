package msvc_gpu.msvc_gpu.Repository;

import msvc_gpu.msvc_gpu.Model.Gpu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuRepository extends JpaRepository<Gpu, Long> {
}