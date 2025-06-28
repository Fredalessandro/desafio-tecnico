package com.desafio.credito.mapper;




import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.entity.Credito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para a conversão entre a entidade Credito e seu DTO.
 * A implementação desta interface é gerada automaticamente pelo MapStruct.
 */
@Mapper(componentModel = "spring") // Gera um Spring Bean (@Component)
public interface CreditoMapper {

  /**
   * Converte uma entidade Credito para CreditoDTO.
   * @param credito A entidade a ser convertida.
   * @return O DTO correspondente.
   */
  CreditoDTO toDTO(Credito credito);

  /**
   * Converte um CreditoDTO para a entidade Credito.
   * O campo 'id' é ignorado, pois geralmente é gerado pelo banco de dados.
   * @param creditoDTO O DTO a ser convertido.
   * @return A entidade correspondente.
   */
  @Mapping(target = "id", ignore = true) // Ignora o ID ao mapear para a entidade
  Credito toEntity(CreditoDTO creditoDTO);
}