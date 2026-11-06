package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.GpuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-gpu", url = "http://localhost:8086/api/gpu")
public interface gpuClient {

    @GetMapping("/{id}")
    GpuDTO getById(@PathVariable Long id);
}
