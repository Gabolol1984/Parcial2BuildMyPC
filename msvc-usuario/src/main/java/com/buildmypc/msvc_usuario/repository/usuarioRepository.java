package com.buildmypc.msvc_usuario.repository;


import com.buildmypc.msvc_usuario.model.Usuario;
import com.buildmypc.msvc_usuario.model.Usuario.EstadoUsuario;
import com.buildmypc.msvc_usuario.model.Usuario.RolFuncional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Usuario save(Usuario usuario);
    List<Usuario> findByEstado(EstadoUsuario estado);
    List<Usuario> findByRolFuncional(RolFuncional rolFuncional);
}
