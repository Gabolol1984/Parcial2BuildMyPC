package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.cpuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cpu", url = "http://localhost:8085/api/v1/cpus")
public interface cpuClient {

    @GetMapping("/{id}")
    cpuDTO getById(@PathVariable Long id);
}
