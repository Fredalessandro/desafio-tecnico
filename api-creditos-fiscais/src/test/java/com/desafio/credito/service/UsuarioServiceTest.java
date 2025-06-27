package com.desafio.credito.service;

import com.desafio.credito.entity.Usuario;
import com.desafio.credito.repository.UsuarioRepository;
import com.desafio.credito.dto.UsuarioRequestDTO;
import com.desafio.credito.dto.UsuarioDTO;
import com.desafio.credito.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
  @Mock
  private UsuarioRepository usuarioRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private UsuarioMapper usuarioMapper;
  @InjectMocks
  private UsuarioService usuarioService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void salvarUsuarioComSenhaCriptografada() {
    UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("João", "joao", "123");
    Usuario usuario = Usuario.builder().nome("João").login("joao").senha("123").build();
    Usuario usuarioSalvo = Usuario.builder().nome("João").login("joao").senha("hash").build();
    UsuarioDTO usuarioDTO = UsuarioDTO.builder().id(1L).nome("João").login("joao").build();
    when(usuarioMapper.toEntity(usuarioRequestDTO)).thenReturn(usuario);
    when(passwordEncoder.encode(any())).thenReturn("hash");
    when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);
    when(usuarioMapper.toDTO(usuarioSalvo)).thenReturn(usuarioDTO);
    UsuarioDTO salvo = usuarioService.salvar(usuarioRequestDTO);
    assertNotNull(salvo);
    assertEquals("João", salvo.getNome());
    verify(passwordEncoder).encode("123");
    verify(usuarioRepository).save(any());
  }

  @Test
  void autenticarRetornarUsuarioSeSenhaCorreta() {
    Usuario usuario = Usuario.builder().login("joao").senha("hash").build();
    when(usuarioRepository.findByLogin("joao")).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches("123", "hash")).thenReturn(true);
    Optional<Usuario> result = usuarioService.autenticar("joao", "123");
    assertTrue(result.isPresent());
  }

  @Test
  void autenticarRetornarVazioSeSenhaIncorreta() {
    Usuario usuario = Usuario.builder().login("joao").senha("hash").build();
    when(usuarioRepository.findByLogin("joao")).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches("321", "hash")).thenReturn(false);
    Optional<Usuario> result = usuarioService.autenticar("joao", "321");
    assertFalse(result.isPresent());
  }

  @Test
  void buscarPorLoginDeveRetornarUsuario() {
    Usuario usuario = Usuario.builder().login("joao").build();
    when(usuarioRepository.findByLogin("joao")).thenReturn(Optional.of(usuario));
    Optional<Usuario> result = usuarioService.buscarPorLogin("joao");
    assertTrue(result.isPresent());
  }

  @Test
  void listarTodosRetornarLista() {
    when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario()));
    List<Usuario> result = usuarioService.listarTodos();
    assertEquals(1, result.size());
  }

  @Test
  void deletarChamarRepository() {
    usuarioService.deletar(1L);
    verify(usuarioRepository).deleteById(1L);
  }
}
