package com.desafio.credito.controller;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.exception.ResourceNotFoundException;
import com.desafio.credito.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditoControllerTest {

  @Mock
  private CreditoService creditoService;
  @InjectMocks
  private CreditoController creditoController;

  private CreditoDTO creditoDTO1;
  private CreditoDTO creditoDTO2;
  private CreditoDTO creditoDTO3;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    creditoDTO1 = CreditoDTO.builder().numeroCredito("123456").numeroNfse("7891011")
        .dataConstituicao(java.time.LocalDate.parse("2024-02-25"))
        .valorIssqn(new java.math.BigDecimal("1500.75")).tipoCredito("ISSQN").simplesNacional(true)
        .aliquota(new java.math.BigDecimal("5")).valorFaturado(new java.math.BigDecimal("30000"))
        .valorDeducao(new java.math.BigDecimal("5000"))
        .baseCalculo(new java.math.BigDecimal("25000")).build();
    creditoDTO2 = CreditoDTO.builder().numeroCredito("789012").numeroNfse("7891011")
        .dataConstituicao(java.time.LocalDate.parse("2024-02-26"))
        .valorIssqn(new java.math.BigDecimal("1200.5")).tipoCredito("ISSQN").simplesNacional(false)
        .aliquota(new java.math.BigDecimal("4.5")).valorFaturado(new java.math.BigDecimal("25000"))
        .valorDeducao(new java.math.BigDecimal("4000"))
        .baseCalculo(new java.math.BigDecimal("21000")).build();
    creditoDTO3 = CreditoDTO.builder().numeroCredito("654321").numeroNfse("1122334")
        .dataConstituicao(java.time.LocalDate.parse("2024-01-15"))
        .valorIssqn(new java.math.BigDecimal("800.5")).tipoCredito("Outros").simplesNacional(true)
        .aliquota(new java.math.BigDecimal("3.5")).valorFaturado(new java.math.BigDecimal("20000"))
        .valorDeducao(new java.math.BigDecimal("3000"))
        .baseCalculo(new java.math.BigDecimal("17000")).build();
  }

  @Test
  void buscarCreditosPorNfse_deveRetornarOk_quandoNumeroValido() {
    when(creditoService.buscarCreditosPorNfse("7891011"))
        .thenReturn(java.util.Arrays.asList(creditoDTO1, creditoDTO2));
    ResponseEntity<List<CreditoDTO>> response = creditoController.buscarCreditosPorNfse("7891011");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(2, response.getBody().size());
    assertEquals("123456", response.getBody().get(0).getNumeroCredito());
    assertEquals("789012", response.getBody().get(1).getNumeroCredito());
  }

  @Test
  void buscarCreditosPorNfse_deveLancarExcecao_quandoNumeroVazio() {
    assertThrows(IllegalArgumentException.class,
        () -> creditoController.buscarCreditosPorNfse(" "));
  }

  @Test
  void buscarCreditosPorNfse_devePropagarExcecao_quandoServiceLancar() {
    when(creditoService.buscarCreditosPorNfse("NFSE-404"))
        .thenThrow(new ResourceNotFoundException("not found"));
    assertThrows(ResourceNotFoundException.class,
        () -> creditoController.buscarCreditosPorNfse("NFSE-404"));
  }

  @Test
  void buscarCreditoPorNumero_deveRetornarOk_quandoNumeroValido() {
    when(creditoService.buscarCreditoPorNumero("654321")).thenReturn(creditoDTO3);
    ResponseEntity<CreditoDTO> response = creditoController.buscarCreditoPorNumero("654321");
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("654321", response.getBody().getNumeroCredito());
    assertEquals("1122334", response.getBody().getNumeroNfse());
    assertEquals("Outros", response.getBody().getTipoCredito());
  }

  @Test
  void buscarCreditoPorNumero_devePropagarExcecao_quandoServiceLancar() {
    when(creditoService.buscarCreditoPorNumero("999"))
        .thenThrow(new ResourceNotFoundException("not found"));
    assertThrows(ResourceNotFoundException.class,
        () -> creditoController.buscarCreditoPorNumero("999"));
  }

  @Test
  void status_deveRetornarOk() {
    ResponseEntity<String> response = creditoController.status();
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("API de Créditos funcionando!", response.getBody());
  }
}
