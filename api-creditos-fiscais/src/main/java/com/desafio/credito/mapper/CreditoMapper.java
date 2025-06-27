package com.desafio.credito.mapper;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.entity.Credito;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditoMapper {
    CreditoDTO toDTO(Credito credito);
}