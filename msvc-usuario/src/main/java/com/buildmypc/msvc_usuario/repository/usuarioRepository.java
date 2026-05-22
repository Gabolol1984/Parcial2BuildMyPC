package com.buildmypc.msvc_usuario.repository;


import com.buildmypc.msvc_usuario.model.usuario;
import com.buildmypc.msvc_usuario.model.usuario.EstadoUsuario;
import com.buildmypc.msvc_usuario.model.usuario.RolFuncional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {
    Optional<usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    usuario save(usuario usuario);
    List<usuario> findByEstado(EstadoUsuario estado);
    List<usuario> findByRolFuncional(RolFuncional rolFuncional);
}
