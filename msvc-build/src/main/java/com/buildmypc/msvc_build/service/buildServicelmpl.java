package com.buildmypc.msvc_build.service;

import com.buildmypc.msvc_build.dto.usuarioDTO;
import com.buildmypc.msvc_build.dto.cpuDTO;
import com.buildmypc.msvc_build.dto.GpuDTO;
import com.buildmypc.msvc_build.dto.listDTO;
import com.buildmypc.msvc_build.client.cpuClient;
import com.buildmypc.msvc_build.client.gpuClient;
import com.buildmypc.msvc_build.client.usuarioClient;
import com.buildmypc.msvc_build.exception.buildException;
import com.buildmypc.msvc_build.repository.buildRepository;
import com.buildmypc.msvc_build.dto.buildDto;
import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.model.build.EstadoBuild;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class buildServicelmpl implements buildService {

    @Autowired
    private buildRepository buildRepository;

    @Autowired
    private usuarioClient usuarioClient;

    @Autowired
    private cpuClient cpuClient;

    @Autowired
    private gpuClient gpuClient;


    @Override
    public List<buildDto> findAll() {
        return this.buildRepository.findAll().stream().map(b->{
            buildDto buildDto = new buildDto();
            buildDto.setId(b.getId());
            buildDto.setCpuId(b.getCpuId());
            buildDto.setGpuId(b.getGpuId());
            buildDto.setUsuarioId(b.getUsuarioId());
            usuarioDTO usuarioDTO = null;
            cpuDTO cpuDTO = null;
            GpuDTO gpuDTO = null;
            try {
                usuarioDTO = usuarioClient.getById(b.getUsuarioId());
                cpuDTO = cpuClient.getById(b.getCpuId());
                gpuDTO = gpuClient.getById(b.getGpuId());
            }catch (FeignException e){
                throw new buildException(e.getMessage());
            }
            listDTO lista = new listDTO();
            lista.setUsuarioId(b.getUsuarioId());
            lista.setCpuId(b.getCpuId());
            lista.setGpuId(b.getGpuId());
            buildDto.setList(lista);

            return buildDto;

        }).toList();

    }

    @Override
    public build getById(Long id) {
        return this.buildRepository.findById(id).orElseThrow(
                () -> new buildException("build not found"));
    }

    @Override
    public build save(build build) {
        try{
            usuarioDTO usuarioDTO = this.usuarioClient.getById(build.getUsuarioId());
        }catch (FeignException exception){
            throw new buildException("El usuario no existe");
        }
        try{
            cpuDTO cpuDTO = this.cpuClient.getById(build.getCpuId());
        }catch (FeignException exception){
            throw new buildException("El cpu no existe");
        }
        try {
            GpuDTO gpuDTO = this.gpuClient.getById(build.getGpuId());
        }catch (FeignException exception){
            throw new buildException("El gpu no existe");
        }
        return this.buildRepository.save(build);
    }

    @Override
    public build updateById(build build, Long id) {
        return this.buildRepository.findById(id).map(b->{
            b.setUsuarioId(build.getUsuarioId());
            b.setCpuId(build.getCpuId());
            b.setGpuId(build.getGpuId());
            try{
                usuarioDTO usuarioDTO = this.usuarioClient.getById(build.getUsuarioId());
                b.setUsuarioId(usuarioDTO.getId());
            }catch (FeignException exception){
                throw new buildException("El usuario no existe");
            }
            return  buildRepository.save(b);
        }).orElseThrow(
                () -> new buildException("la build no existe")
        );
    }

    @Override
    public void deleteById(Long id) {
        this.buildRepository.deleteById(id);

    }

    @Override
    public buildDto cambiarEstado(Long id, String nuevoEstado) {
        return null;
    }
}
