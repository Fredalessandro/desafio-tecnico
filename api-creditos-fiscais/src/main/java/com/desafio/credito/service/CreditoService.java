package com.desafio.credito.service;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.entity.Credito;
import com.desafio.credito.exception.ResourceNotFoundException;
import com.desafio.credito.mapper.CreditoMapper;
import com.desafio.credito.repository.CreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditoService {

  private final CreditoMapper creditoMapper;
  private final CreditoRepository creditoRepository;

  public List<CreditoDTO> buscarCreditosPorNfse(String numeroNfse) {
    List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);

    if (creditos.isEmpty()) {
      throw new ResourceNotFoundException("Nenhum crédito encontrado para a NFS-e: " + numeroNfse);
    }

    return creditos.stream()
            .map(creditoMapper::toDTO)
            .collect(Collectors.toList());
  }

  public CreditoDTO buscarCreditoPorNumero(String numeroCredito) {
    return creditoRepository.findByNumeroCredito(numeroCredito).map(creditoMapper::toDTO)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Crédito não encontrado com o número: " + numeroCredito));
  }

}
