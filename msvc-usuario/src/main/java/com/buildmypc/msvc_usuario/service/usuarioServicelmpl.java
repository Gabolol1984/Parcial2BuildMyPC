package com.buildmypc.msvc_usuario.service;

import com.buildmypc.msvc_usuario.dto.UsuarioRequestDTO;
import com.buildmypc.msvc_usuario.dto.UsuarioResponseDTO;
import com.buildmypc.msvc_usuario.exception.ResourceNotFoundException;
import com.buildmypc.msvc_usuario.exception.ServiceException;
import com.buildmypc.msvc_usuario.model.Usuario;
import com.buildmypc.msvc_usuario.model.Usuario.EstadoUsuario;
import com.buildmypc.msvc_usuario.model.Usuario.RolFuncional;
import com.buildmypc.msvc_usuario.repository.usuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.List;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class usuarioServicelmpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(usuarioServicelmpl.class);

    private final usuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        log.info("Creando usuario con email={}", dto.getEmail());

        // Regla: no se deben duplicar perfiles con el mismo correo
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Intento de crear usuario con email duplicado: {}", dto.getEmail());
            throw new ServiceException(
                    "Ya existe un usuario registrado con el email: " + dto.getEmail());
        }

        RolFuncional rol = parsearRol(dto.getRolFuncional());

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .rolFuncional(rol)
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con id={}", guardado.getId());
        return toDTO(guardado);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listarTodos() {
        log.info("Listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    @Override
    public List<UsuarioResponseDTO> listarPorRol(String rol) {
        log.info("Listando usuarios por rol={}", rol);
        RolFuncional rolFuncional = parsearRol(rol);
        return usuarioRepository.findByRolFuncional(rolFuncional)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> listarPorEstado(String estado) {
        log.info("Listando usuarios por estado={}", estado);
        EstadoUsuario estadoUsuario = parsearEstado(estado);
        return usuarioRepository.findByEstado(estadoUsuario)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        log.info("Buscando usuario con id={}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con id={}", id);
                    return new ResourceNotFoundException(
                            "Usuario no encontrado con id: " + id);
                });
        return toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO buscarPorEmail(String email) {
        log.info("Buscando usuario con email={}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con email={}", email);
                    return new ResourceNotFoundException(
                            "Usuario no encontrado con email: " + email);
                });
        return toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        log.info("Actualizando usuario con id={}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));

        // Regla: si cambia el email, verificar que no esté tomado por otro usuario
        if (!usuario.getEmail().equalsIgnoreCase(dto.getEmail())
                && usuarioRepository.existsByEmail(dto.getEmail())) {
            log.warn("Email {} ya está en uso por otro usuario", dto.getEmail());
            throw new ServiceException(
                    "El email " + dto.getEmail() + " ya está registrado por otro usuario.");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRolFuncional(parsearRol(dto.getRolFuncional()));

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario id={} actualizado correctamente", id);
        return toDTO(actualizado);
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        log.info("Desactivando usuario con id={}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));

        // Regla: desactivación lógica (no eliminación física)
        // Un usuario con builds asociadas nunca debe eliminarse físicamente
        usuario.setEstado(EstadoUsuario.INACTIVO);
        usuarioRepository.save(usuario);
        log.info("Usuario id={} desactivado correctamente", id);
    }

    // ----------- Helpers -----------

    private RolFuncional parsearRol(String rol) {
        try {
            return RolFuncional.valueOf(rol.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Rol inválido: " + rol
                    + ". Valores válidos: USUARIO, TECNICO, ADMINISTRADOR");
        }
    }

    private EstadoUsuario parsearEstado(String estado) {
        try {
            return EstadoUsuario.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Estado inválido: " + estado
                    + ". Valores válidos: ACTIVO, INACTIVO");
        }
    }

    // ----------- Mapper -----------
    private UsuarioResponseDTO toDTO(Usuario u) {
        return UsuarioResponseDTO.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .email(u.getEmail())
                .telefono(u.getTelefono())
                .rolFuncional(u.getRolFuncional())
                .estado(u.getEstado())
                .fechaRegistro(u.getFechaRegistro())
                .build();
    }
}
