package com.desafio.credito.mapper;

import com.desafio.credito.dto.UsuarioDTO;
import com.desafio.credito.entity.Usuario;
import com.desafio.credito.dto.UsuarioRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
  UsuarioDTO toDTO(Usuario usuario);

  Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO);

  @Mapping(target = "id", ignore = true)
  Usuario toEntity(UsuarioDTO usuarioDTO);
}
