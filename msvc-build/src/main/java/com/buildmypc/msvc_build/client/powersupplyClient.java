package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.powersupplyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "msvc-powerSupply",
        url = "http://localhost:8089"
)
public interface powersupplyClient {

    @GetMapping("/api/v1/psus/{id}")
    powersupplyDTO getById(@PathVariable("id") Long id);
}
