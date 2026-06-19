package com.buildmypc.msvc_usuario.repositories;

import com.buildmypc.msvc_usuario.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
    // Busca un rol por su nombre (ej: ). Lo usan el registro y el seed inicial.
    Optional<Rol> findByNombre(String nombre);
}
