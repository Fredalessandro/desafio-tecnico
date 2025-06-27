package com.desafio.credito.service;

import com.desafio.credito.entity.Usuario;
import com.desafio.credito.repository.UsuarioRepository;
import com.desafio.credito.dto.UsuarioDTO;
import com.desafio.credito.dto.UsuarioRequestDTO;
import com.desafio.credito.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final UsuarioMapper usuarioMapper;

  public UsuarioDTO salvar(UsuarioRequestDTO usuarioRequestDTO) {
    if (usuarioRepository.findByLogin(usuarioRequestDTO.getLogin()).isPresent()) {
      throw new IllegalArgumentException("Login já está em uso");
    }
    Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    Usuario salvo = usuarioRepository.save(usuario);
    return usuarioMapper.toDTO(salvo);
  }

  public Optional<Usuario> buscarPorId(Long id) {
    return usuarioRepository.findById(id);
  }

  public Optional<Usuario> buscarPorLogin(String login) {
    return usuarioRepository.findByLogin(login);
  }

  public List<Usuario> listarTodos() {
    return usuarioRepository.findAll();
  }

  public void deletar(Long id) {
    usuarioRepository.deleteById(id);
  }

  public Optional<Usuario> autenticar(String login, String senha) {
    return usuarioRepository.findByLogin(login)
        .filter(usuario -> passwordEncoder.matches(senha, usuario.getSenha()));
  }
}
