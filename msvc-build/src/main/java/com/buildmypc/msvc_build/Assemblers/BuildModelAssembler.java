package com.buildmypc.msvc_build.Assemblers;



import com.buildmypc.msvc_build.controller.buildControllerV2;
import com.buildmypc.msvc_build.model.build;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BuildModelAssembler
        implements RepresentationModelAssembler<build, EntityModel<build>> {

    @Override
    public EntityModel<build> toModel(build build) {

        return EntityModel.of(build,

                linkTo(methodOn(buildControllerV2.class)
                        .findById(build.getId()))
                        .withSelfRel(),

                linkTo(methodOn(buildControllerV2.class)
                        .findAll())
                        .withRel("builds"),

                linkTo(methodOn(buildControllerV2.class)
                        .save(null))
                        .withRel("crear"),

                linkTo(methodOn(buildControllerV2.class)
                        .updateById(build.getId(), null))
                        .withRel("actualizar"),

                linkTo(methodOn(buildControllerV2.class)
                        .deleteById(build.getId()))
                        .withRel("eliminar"),

                linkTo(methodOn(buildControllerV2.class)
                        .cambiarEstado(build.getId(), null))
                        .withRel("cambiar-estado")
        );
    }
}