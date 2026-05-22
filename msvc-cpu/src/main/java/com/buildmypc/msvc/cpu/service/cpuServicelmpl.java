package com.buildmypc.msvc.cpu.service;

import com.buildmypc.msvc.cpu.exception.cpuException;
import com.buildmypc.msvc.cpu.model.cpu;
import com.buildmypc.msvc.cpu.repository.cpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class cpuServicelmpl implements cpuService {

    @Autowired
    private cpuRepository cpuRepository;

    @Transactional
    @Override
    public cpu save(cpu cpu) {
        if (this.cpuRepository.findByName(cpu.getCpuName()).isPresent()) {
            throw new cpuException("cpu existente");
        }
        return this.cpuRepository.save(cpu);
    }

    @Transactional(readOnly = true)
    @Override
    public List<cpu> getAll(){return this.cpuRepository.findAll();}


    @Transactional(readOnly = true)
    @Override
    public cpu getById(Long id) {
        return this.cpuRepository.findById(id).orElseThrow(
                () -> new cpuException("cpu con el  id"+ id + "no existe")
        );
    }

    @Override
    public cpu updateById(Long id,cpu cpu) {
        return this.cpuRepository.findById(id).map(element->{
            element.setSocket(cpu.getSocket());
            element.setNucleos(cpu.getNucleos());
            element.setHilos(cpu.getHilos());
            element.setFrecuenciaBase(cpu.getFrecuenciaBase());
            element.setTdpWatts(cpu.getTdpWatts());
            element.setGeneracion(cpu.getGeneracion());
            element.setSoportaDdr4(cpu.getSoportaDdr4());
            element.setSoportaDdr5(cpu.getSoportaDdr5());
            return this.cpuRepository.save(element);
        }).orElseThrow(() -> new cpuException("cpu con el  id"+ id + "no existe"));
    }

    @Override
    public void delete(Long id) {
        this.cpuRepository.deleteById(id);
    }

}
