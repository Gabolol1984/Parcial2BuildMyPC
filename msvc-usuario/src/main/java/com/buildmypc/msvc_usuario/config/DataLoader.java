package com.buildmypc.msvc_usuario.config;

import com.buildmypc.msvc_usuario.models.Rol;
import com.buildmypc.msvc_usuario.models.Usuario;
import com.buildmypc.msvc_usuario.repositories.RolRepository;
import com.buildmypc.msvc_usuario.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

// Siembra datos al arrancar: los 3 roles y 3 usuarios de prueba (uno por rol). ROLE_ADMIN, ROLE_TECNICO, ROLE_USUARIO.
// Asi la demo tiene credenciales listas sin tener que registrarse a mano.
@Component
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ROLE_ADMIN, ROLE_TECNICO, ROLE_USUARIO.
    @Override
    public void run(String... args) {
        Rol admin = obtenerOCrearRol("ROLE_ADMIN");
        Rol tecnico = obtenerOCrearRol("ROLE_TECNICO");
        Rol usuario = obtenerOCrearRol("ROLE_USUARIO");

        crearUsuarioSiNoExiste("admin", "admin123", Set.of(admin));
        crearUsuarioSiNoExiste("tecnico1", "tecnico123", Set.of(tecnico));
        crearUsuarioSiNoExiste("usuario1", "usuario123", Set.of(usuario));
    }

    private Rol obtenerOCrearRol(String nombre) {
        return this.rolRepository.findByNombre(nombre).orElseGet(() -> this.rolRepository.save(new Rol(nombre)));
    }

    private void crearUsuarioSiNoExiste(String username, String passwordPlano, Set<Rol> roles) {
        if (this.usuarioRepository.existsByUsername(username)) {
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(this.passwordEncoder.encode(passwordPlano));
        usuario.setRoles(roles);
        this.usuarioRepository.save(usuario);
    }
}
