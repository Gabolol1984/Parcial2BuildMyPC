package com.buildmypc.msvc_build.service;

import com.buildmypc.msvc_build.client.*;
import com.buildmypc.msvc_build.dto.*;
import com.buildmypc.msvc_build.exception.buildException;
import com.buildmypc.msvc_build.repository.buildRepository;
import com.buildmypc.msvc_build.model.build;
import com.buildmypc.msvc_build.model.build.EstadoBuild;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private MotherboardClient motherboardClient;

    @Autowired
    private ramClient ramClient;

    @Autowired
    private powersupplyClient powersupplyClient;


    @Transactional(readOnly = true)
    @Override
    public List<buildDto> findAll() {
        return this.buildRepository.findAll().stream().map(b -> {
            buildDto buildDto = new buildDto();
            buildDto.setId(b.getId());
            buildDto.setCpuId(b.getCpuId());
            buildDto.setGpuId(b.getGpuId());
            buildDto.setUsuarioId(b.getUsuarioId());

            if (b.getUsuarioId() != null) {
                try {
                    usuarioClient.getById(b.getUsuarioId());
                } catch (FeignException ignored) {
                }
            }
            if (b.getCpuId() != null) {
                try {
                    cpuClient.getById(b.getCpuId());
                } catch (FeignException ignored) {
                }
            }
            if (b.getGpuId() != null) {
                try {
                    gpuClient.getById(b.getGpuId());
                } catch (FeignException ignored) {
                }
            }

            listDTO lista = new listDTO();
            lista.setUsuarioId(b.getUsuarioId());
            lista.setCpuId(b.getCpuId());
            lista.setGpuId(b.getGpuId());
            buildDto.setList(lista);

            return buildDto;
        }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public build getById(Long id) {
        return this.buildRepository.findById(id).orElseThrow(
                () -> new buildException("build not found"));
    }

    @Transactional
    @Override
    public build save(build build) {
        if (build.getEstado() == null) {
            build.setEstado(EstadoBuild.BORRADOR);
        }

        if (build.getUsuarioId() != null) {
            try {
                this.usuarioClient.getById(build.getUsuarioId());
            } catch (FeignException exception) {
                throw new buildException("El usuario no existe");
            }
        }

        if (build.getCpuId() != null) {
            try {
                this.cpuClient.getById(build.getCpuId());
            } catch (FeignException exception) {
                throw new buildException("El cpu no existe");
            }
        }

        if (build.getGpuId() != null) {
            try {
                this.gpuClient.getById(build.getGpuId());
            } catch (FeignException exception) {
                throw new buildException("El gpu no existe");
            }
        }

        if (build.getMotherboardId() != null) {
            try {
                this.motherboardClient.getById(build.getMotherboardId());
            } catch (FeignException exception) {
                throw new buildException("La motherboard no existe");
            }
        }

        if (build.getRamId() != null) {
            try {
                this.ramClient.getById(build.getRamId());
            } catch (FeignException exception) {
                throw new buildException("La RAM no existe");
            }
        }

        if (build.getFuenteId() != null) {
            try {
                this.powersupplyClient.getById(build.getFuenteId());
            } catch (FeignException exception) {
                throw new buildException("La fuente de poder no existe");
            }
        }

        return this.buildRepository.save(build);
    }

    @Transactional
    @Override
    public build updateById(build build, Long id) {
        return this.buildRepository.findById(id).map(b -> {
            b.setUsuarioId(build.getUsuarioId());
            b.setCpuId(build.getCpuId());
            b.setGpuId(build.getGpuId());
            b.setMotherboardId(build.getMotherboardId());
            b.setRamId(build.getRamId());
            b.setFuenteId(build.getFuenteId());
            if (build.getEstado() != null) {
                b.setEstado(build.getEstado());
            }
            if (build.getUsuarioId() != null) {
                try {
                    usuarioDTO usuarioDTO = this.usuarioClient.getById(build.getUsuarioId());
                    b.setUsuarioId(usuarioDTO.getId());
                } catch (FeignException exception) {
                    throw new buildException("El usuario no existe");
                }
            }
            return buildRepository.save(b);
        }).orElseThrow(
                () -> new buildException("la build no existe")
        );
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.buildRepository.deleteById(id);
    }

    @Transactional
    @Override
    public buildDto cambiarEstado(Long id, String nuevoEstado) {
        build b = this.buildRepository.findById(id).orElseThrow(
                () -> new buildException("Build no encontrada con id: " + id));
        try {
            b.setEstado(EstadoBuild.valueOf(nuevoEstado.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new buildException("Estado inválido: " + nuevoEstado);
        }
        build guardada = this.buildRepository.save(b);
        buildDto dto = new buildDto();
        dto.setId(guardada.getId());
        dto.setUsuarioId(guardada.getUsuarioId());
        dto.setCpuId(guardada.getCpuId());
        dto.setGpuId(guardada.getGpuId());
        return dto;
    }
}
