package com.buildmypc.msvc_quotation.feign;

import com.buildmypc.msvc_quotation.feign.dto.BuildDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-build", url = "http://localhost:8084")
public interface BuildClient {

    @GetMapping("/api/v1/builds/{id}")
    BuildDTO getBuildById(@PathVariable("id") Long id);

    // Notifica a build-service el cambio de estado tras cotizar
    @PatchMapping("/api/v1/builds/{id}/estado")
    void cambiarEstado(@PathVariable("id") Long id, @RequestParam("nuevoEstado") String nuevoEstado);
}
