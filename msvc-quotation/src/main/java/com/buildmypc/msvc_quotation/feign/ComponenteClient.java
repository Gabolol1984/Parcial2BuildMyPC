package com.buildmypc.msvc_quotation.feign;


import com.buildmypc.msvc_quotation.feign.dto.ComponenteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "component-service",url = "http://localhost:8083")
public interface ComponenteClient {
    @GetMapping("/api/v1/componentes/{id}")
    ComponenteDTO getComponenteById(@PathVariable Long id);
}
