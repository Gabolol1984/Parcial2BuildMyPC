package com.buildmypc.msvc_usuario.service;

import com.buildmypc.msvc_usuario.dto.UsuarioRequestDTO;
import com.buildmypc.msvc_usuario.dto.UsuarioResponseDTO;
import com.buildmypc.msvc_usuario.model.Usuario;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crear(UsuarioRequestDTO dto);

    List<Usuario> listarTodos();

    List<UsuarioResponseDTO> listarPorRol(String rol);

    List<UsuarioResponseDTO> listarPorEstado(String estado);

    UsuarioResponseDTO buscarPorId(Long id);

    UsuarioResponseDTO buscarPorEmail(String email);

    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto);

    void desactivar(Long id);
}
