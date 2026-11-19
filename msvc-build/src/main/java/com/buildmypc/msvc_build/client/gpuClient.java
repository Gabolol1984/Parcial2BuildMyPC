package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.GpuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-gpu", url = "http://localhost:8086/api/v1/gpu")
public interface gpuClient {

    @GetMapping("/{id}")
    GpuDTO getById(@PathVariable("id") Long id);
}
