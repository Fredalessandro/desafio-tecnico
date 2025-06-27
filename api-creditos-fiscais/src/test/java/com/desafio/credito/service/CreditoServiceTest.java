package com.desafio.credito.service;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.entity.Credito;
import com.desafio.credito.exception.ResourceNotFoundException;
import com.desafio.credito.mapper.CreditoMapper;
import com.desafio.credito.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditoServiceTest {

  @Mock
  private CreditoRepository creditoRepository;
  @Mock
  private CreditoMapper creditoMapper;
  @InjectMocks
  private CreditoService creditoService;

  private Credito credito1;
  private Credito credito2;
  private Credito credito3;
  private CreditoDTO creditoDTO1;
  private CreditoDTO creditoDTO2;
  private CreditoDTO creditoDTO3;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    credito1 = Credito.builder().id(1L).numeroCredito("123456").numeroNfse("7891011")
        .dataConstituicao(LocalDate.parse("2024-02-25")).valorIssqn(new BigDecimal("1500.75"))
        .tipoCredito("ISSQN").simplesNacional(true).aliquota(new BigDecimal("5"))
        .valorFaturado(new BigDecimal("30000")).valorDeducao(new BigDecimal("5000"))
        .baseCalculo(new BigDecimal("25000")).build();
    credito2 = Credito.builder().id(2L).numeroCredito("789012").numeroNfse("7891011")
        .dataConstituicao(LocalDate.parse("2024-02-26")).valorIssqn(new BigDecimal("1200.5"))
        .tipoCredito("ISSQN").simplesNacional(false).aliquota(new BigDecimal("4.5"))
        .valorFaturado(new BigDecimal("25000")).valorDeducao(new BigDecimal("4000"))
        .baseCalculo(new BigDecimal("21000")).build();
    credito3 = Credito.builder().id(3L).numeroCredito("654321").numeroNfse("1122334")
        .dataConstituicao(LocalDate.parse("2024-01-15")).valorIssqn(new BigDecimal("800.5"))
        .tipoCredito("Outros").simplesNacional(true).aliquota(new BigDecimal("3.5"))
        .valorFaturado(new BigDecimal("20000")).valorDeducao(new BigDecimal("3000"))
        .baseCalculo(new BigDecimal("17000")).build();
    creditoDTO1 = CreditoDTO.builder().numeroCredito("123456").numeroNfse("7891011")
        .dataConstituicao(LocalDate.parse("2024-02-25")).valorIssqn(new BigDecimal("1500.75"))
        .tipoCredito("ISSQN").simplesNacional(true).aliquota(new BigDecimal("5"))
        .valorFaturado(new BigDecimal("30000")).valorDeducao(new BigDecimal("5000"))
        .baseCalculo(new BigDecimal("25000")).build();
    creditoDTO2 = CreditoDTO.builder().numeroCredito("789012").numeroNfse("7891011")
        .dataConstituicao(LocalDate.parse("2024-02-26")).valorIssqn(new BigDecimal("1200.5"))
        .tipoCredito("ISSQN").simplesNacional(false).aliquota(new BigDecimal("4.5"))
        .valorFaturado(new BigDecimal("25000")).valorDeducao(new BigDecimal("4000"))
        .baseCalculo(new BigDecimal("21000")).build();
    creditoDTO3 = CreditoDTO.builder().numeroCredito("654321").numeroNfse("1122334")
        .dataConstituicao(LocalDate.parse("2024-01-15")).valorIssqn(new BigDecimal("800.5"))
        .tipoCredito("Outros").simplesNacional(true).aliquota(new BigDecimal("3.5"))
        .valorFaturado(new BigDecimal("20000")).valorDeducao(new BigDecimal("3000"))
        .baseCalculo(new BigDecimal("17000")).build();
  }

  @Test
  void buscarCreditosPorNfse_deveRetornarListaDTO_quandoEncontrarCreditos() {
    when(creditoRepository.findByNumeroNfse("7891011"))
        .thenReturn(Arrays.asList(credito1, credito2));
    when(creditoMapper.toDTO(credito1)).thenReturn(creditoDTO1);
    when(creditoMapper.toDTO(credito2)).thenReturn(creditoDTO2);
    List<CreditoDTO> result = creditoService.buscarCreditosPorNfse("7891011");
    assertEquals(2, result.size());
    assertEquals("123456", result.get(0).getNumeroCredito());
    assertEquals("789012", result.get(1).getNumeroCredito());
  }

  @Test
  void buscarCreditosPorNfse_deveLancarExcecao_quandoNaoEncontrarCreditos() {
    when(creditoRepository.findByNumeroNfse("0000000")).thenReturn(Collections.emptyList());
    assertThrows(ResourceNotFoundException.class,
        () -> creditoService.buscarCreditosPorNfse("0000000"));
  }

  @Test
  void buscarCreditoPorNumero_deveRetornarDTO_quandoEncontrarCredito() {
    when(creditoRepository.findByNumeroCredito("654321")).thenReturn(Optional.of(credito3));
    when(creditoMapper.toDTO(credito3)).thenReturn(creditoDTO3);
    CreditoDTO result = creditoService.buscarCreditoPorNumero("654321");
    assertNotNull(result);
    assertEquals("654321", result.getNumeroCredito());
    assertEquals("1122334", result.getNumeroNfse());
    assertEquals("Outros", result.getTipoCredito());
  }

  @Test
  void buscarCreditoPorNumero_deveLancarExcecao_quandoNaoEncontrarCredito() {
    when(creditoRepository.findByNumeroCredito("999999")).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class,
        () -> creditoService.buscarCreditoPorNumero("999999"));
  }
}
