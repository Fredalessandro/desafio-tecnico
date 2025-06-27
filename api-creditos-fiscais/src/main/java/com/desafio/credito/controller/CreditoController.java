package com.desafio.credito.controller;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.service.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@RequiredArgsConstructor
public class CreditoController {

  private final CreditoService creditoService;

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
}
