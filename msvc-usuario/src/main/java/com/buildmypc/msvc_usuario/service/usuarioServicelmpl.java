package com.buildmypc.msvc_usuario.service;

import com.buildmypc.msvc_usuario.exception.usuarioException;
import com.buildmypc.msvc_usuario.model.usuario;
import com.buildmypc.msvc_usuario.repository.usuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class usuarioServicelmpl implements usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    @Transactional
    @Override
    public usuario save(usuario usuario) {
        if (this.usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new usuarioException("usuario existente");
        }
        return this.usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public List<usuario> getAll() {
        return this.usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public usuario getById(Long id) {
        return this.usuarioRepository.findById(id).orElseThrow(
                ()-> new usuarioException("el usuario con el id: "+ id + "no existe")
        );
    }

    @Override
    public usuario getByEmail(String email) {
        return this.usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new usuarioException("el usuario con el email: "+ email + "no existe")
        );
    }

    @Override
    public usuario updateById(Long id, usuario usuario) {
        return this.usuarioRepository.findById(id).map(element->{
            element.setNombre(usuario.getNombre());
            element.setApellido(usuario.getApellido());
            element.setEmail(usuario.getEmail());
            element.setEstado(usuario.getEstado());
            return this.usuarioRepository.save(element);
        }).orElseThrow(()->new usuarioException("el usuario con el id: "+ id + "no existe"));
    }

    @Override
    public void deleteById(Long id) {
        this.usuarioRepository.deleteById(id);
    }
}
