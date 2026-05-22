package com.buildmypc.msvc_build.service;

import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.dto.buildDto;
import java.util.List;

public interface buildService {
    List<buildDto> findAll();
    build getById(Long id);
    build save(build build);
    build updateById(build build,Long id);
    void deleteById(Long id);

}
