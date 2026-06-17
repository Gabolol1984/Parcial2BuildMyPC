package com.buildmypc.msvc_usuario.assemblers;


import com.buildmypc.msvc_usuario.controller.usuarioController;
import com.buildmypc.msvc_usuario.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(
                usuario,
                linkTo(methodOn(usuarioController.class).buscarPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(usuarioController.class).listarTodos()).withRel("usuario")
        );
    }
}
