package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.usuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", url = "localhost:8082/api/v1/usuarios")
public interface usuarioClient {

    @GetMapping("/{id}")
    usuarioDTO getById(@PathVariable Long id);
}
