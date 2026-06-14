package com.buildmypc.msvc.cpu.Assemblers;


import com.buildmypc.msvc.cpu.controller.cpuControllerV2;
import com.buildmypc.msvc.cpu.model.cpu;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class cpuModelAssemblers implements RepresentationModelAssembler<cpu, EntityModel<cpu>> {

    @Override
    public EntityModel<cpu> toModel(cpu Cpu) {

        return EntityModel.of(Cpu,
                linkTo(methodOn(cpuControllerV2.class)
                        .findById(Cpu.getId())).withSelfRel(),

                linkTo(methodOn(cpuControllerV2.class)
                        .findAll()).withRel("cpus"),

                linkTo(methodOn(cpuControllerV2.class)
                        .save(null)).withRel("crear"),

                linkTo(methodOn(cpuControllerV2.class)
                        .updateById(Cpu.getId(), null)).withRel("actualizar"),

                linkTo(methodOn(cpuControllerV2.class)
                        .deleteById(Cpu.getId())).withRel("eliminar")
        );
    }
}