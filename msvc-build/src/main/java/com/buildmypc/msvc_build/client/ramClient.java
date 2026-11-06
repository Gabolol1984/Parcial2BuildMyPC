package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.ramDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "msvc-ram",
        url = "http://localhost:8086"
)
public interface ramClient {

    @GetMapping("/api/v1/rams/{id}")
    ramDTO getById(@PathVariable Long id);
}