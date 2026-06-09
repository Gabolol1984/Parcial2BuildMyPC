package com.buildmypc.msvc_usuario.services;


import com.buildmypc.msvc_usuario.dto.UsuarioResponseDTO;
import com.buildmypc.msvc_usuario.model.Usuario;
import com.buildmypc.msvc_usuario.service.usuarioServicelmpl;
import com.buildmypc.msvc_usuario.repository.usuarioRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private usuarioRepository usuarioRepository;

    @InjectMocks
    private usuarioServicelmpl usuarioService;

    private Usuario usuarioPrueba;
    private List<Usuario> usuarioList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        this.usuarioPrueba=new Usuario();
        this.usuarioPrueba.setApellido("Rojas");
        this.usuarioPrueba.setNombre("Juan");
        this.usuarioPrueba.setEmail("juan_test@correo.cl");
        this.usuarioPrueba.setTelefono("00000000");
        this.usuarioPrueba.setEstado(Usuario.EstadoUsuario.ACTIVO);
        this.usuarioPrueba.setRolFuncional(Usuario.RolFuncional.USUARIO);

        Faker faker = new Faker(Locale.of("es","CL"));
        for (int i = 0; 1< 100; i++){
            Usuario usuario = new Usuario();

            usuarioList.add(usuario);
        }

    }

    @Test
    @DisplayName("should list all users")
    public void shouldListAllUsers(){
        //Arrange
        List<Usuario> usuarios = this.usuarioList;
        usuarios.add(this.usuarioPrueba);
        when(this.usuarioRepository.findAll()).thenReturn(usuarios);

        //ACT
        List<UsuarioResponseDTO> result = this.usuarioService.listarTodos();

        //ASSERT
        assertThat(result).hasSize(101);
        assertThat(result).contains(usuarioPrueba);
        verify(usuarioRepository, times(1)).listarTodos();

    }
}
