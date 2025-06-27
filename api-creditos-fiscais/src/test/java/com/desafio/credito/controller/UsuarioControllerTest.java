package com.desafio.credito.controller;

import com.desafio.credito.config.JwtUtil;
import com.desafio.credito.entity.Usuario;
import com.desafio.credito.service.UsuarioService;
import com.desafio.credito.dto.UsuarioDTO;
import com.desafio.credito.dto.LoginRequestDTO;
import com.desafio.credito.dto.UsuarioRequestDTO;
import com.desafio.credito.dto.LoginResponseDTO;
import com.desafio.credito.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {
  @Mock
  private UsuarioService usuarioService;
  @Mock
  private JwtUtil jwtUtil;
  @Mock
  private UsuarioMapper usuarioMapper;
  @InjectMocks
  private UsuarioController usuarioController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void criarDeveRetornarUsuarioCriado() {
    UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("João", "joao", "senha");
    UsuarioDTO usuarioDTO = UsuarioDTO.builder().id(1L).nome("João").login("joao").build();
    when(usuarioService.salvar(any())).thenReturn(usuarioDTO);
    ResponseEntity<UsuarioDTO> response = usuarioController.criar(usuarioRequestDTO);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(usuarioDTO, response.getBody());
  }

  @Test
  void listarDeveRetornarListaUsuarios() {
    when(usuarioService.listarTodos()).thenReturn(List.of(new Usuario()));
    when(usuarioMapper.toDTO(any()))
        .thenReturn(UsuarioDTO.builder().id(1L).nome("João").login("joao").build());
    ResponseEntity<List<UsuarioDTO>> response = usuarioController.listar();
    assertEquals(1, response.getBody().size());
    assertEquals("João", response.getBody().get(0).getNome());
  }

  @Test
  void buscarPorIdDeveRetornarUsuario() {
    Usuario usuario = Usuario.builder().id(1L).nome("João").login("joao").build();
    UsuarioDTO usuarioDTO = UsuarioDTO.builder().id(1L).nome("João").login("joao").build();
    when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));
    when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);
    ResponseEntity<UsuarioDTO> response = usuarioController.buscarPorId(1L);
    assertEquals(usuarioDTO, response.getBody());
  }

  @Test
  void buscarPorIdDeveRetornarNotFound() {
    when(usuarioService.buscarPorId(1L)).thenReturn(Optional.empty());
    ResponseEntity<UsuarioDTO> response = usuarioController.buscarPorId(1L);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void deletarDeveRetornarNoContent() {
    ResponseEntity<Void> response = usuarioController.deletar(1L);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(usuarioService).deletar(1L);
  }

  @Test
  void loginDeveRetornarTokenSeAutenticado() {
    Usuario usuario = Usuario.builder().nome("João da Silva").login("joao").senha("123").build();
    when(usuarioService.autenticar("joao", "123")).thenReturn(Optional.of(usuario));
    when(jwtUtil.gerarToken("joao")).thenReturn("token123");
    LoginRequestDTO loginRequest = new LoginRequestDTO();
    loginRequest.setLogin("joao");
    loginRequest.setSenha("123");
    LoginResponseDTO expected = new LoginResponseDTO("João da Silva", "joao", "token123");
    ResponseEntity<?> response = usuarioController.login(loginRequest);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof LoginResponseDTO);
    LoginResponseDTO actual = (LoginResponseDTO) response.getBody();
    assertEquals(expected.getNome(), actual.getNome());
    assertEquals(expected.getLogin(), actual.getLogin());
    assertEquals(expected.getToken(), actual.getToken());
  }

  @Test
  void loginDeveRetornarUnauthorizedSeFalhar() {
    when(usuarioService.autenticar("joao", "errada")).thenReturn(Optional.empty());
    LoginRequestDTO loginRequest = new LoginRequestDTO();
    loginRequest.setLogin("joao");
    loginRequest.setSenha("errada");
    ResponseEntity<?> response = usuarioController.login(loginRequest);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
}
