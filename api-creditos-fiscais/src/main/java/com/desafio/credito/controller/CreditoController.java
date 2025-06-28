package com.desafio.credito.controller;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.service.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/creditos")
@RequiredArgsConstructor
public class CreditoController {

  private final CreditoService creditoService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/{numeroNfse}")
  public ResponseEntity<List<CreditoDTO>> buscarCreditosPorNfse(@PathVariable String numeroNfse) {
    if (numeroNfse == null || numeroNfse.trim().isEmpty()) {
      throw new IllegalArgumentException("Número da NFS-e é obrigatório");
    }

    List<CreditoDTO> creditos = creditoService.buscarCreditosPorNfse(numeroNfse);
    return ResponseEntity.ok(creditos);
  }

  @GetMapping("/credito/{numeroCredito}")
  public ResponseEntity<CreditoDTO> buscarCreditoPorNumero(@PathVariable String numeroCredito) {
    CreditoDTO credito = creditoService.buscarCreditoPorNumero(numeroCredito);
    return ResponseEntity.ok(credito);
  }

  @GetMapping("/status")
  public ResponseEntity<String> status() {
    return ResponseEntity.ok("API de Créditos funcionando!");
  }

  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> health() {
    try {
      // Testa a conexão com o banco de dados
      jdbcTemplate.queryForObject("SELECT 1", Integer.class);

      Map<String, Object> healthStatus = Map.of("status", "UP", "database", "UP", "message",
          "API de Créditos funcionando corretamente!");

      return ResponseEntity.ok(healthStatus);
    } catch (Exception e) {
      Map<String, Object> healthStatus = Map.of("status", "DOWN", "database", "DOWN", "message",
          "Erro na conexão com o banco de dados: " + e.getMessage());

      return ResponseEntity.status(503).body(healthStatus);
    }
  }
}
