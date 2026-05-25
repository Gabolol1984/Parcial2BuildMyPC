 package com.buildmypc.msvc_build.client;

import com.buildmypc.msvc_build.dto.motherboardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

    @FeignClient(
            name = "msvc-motherboard",
            url = "http://localhost:8084"
    )
    public interface MotherboardClient {

        @GetMapping("/api/v1/motherboards/{id}")
        motherboardDTO getById(@PathVariable Long id);
    }

