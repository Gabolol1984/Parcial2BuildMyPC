package com.buildmypc.msvc_usuario.service;

import com.buildmypc.msvc_usuario.model.usuario;

import java.util.List;

public interface usuarioService {
    List<usuario> getAll();
    usuario getById(Long id);
    usuario getByEmail(String email);
    usuario save(usuario usuario);
    usuario updateById(Long id, usuario usuario);
    void deleteById(Long id);
}
