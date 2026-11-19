package msvc.gpu.client;

import msvc.gpu.Dto.buildDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-build", url = "http://localhost:8084/api/v1/builds")
public interface buildClient {

    @GetMapping("/{id}")
    buildDTO getBuildById(@PathVariable("id") Long id);

    @GetMapping
    List<buildDTO> getAllBuilds();
}
