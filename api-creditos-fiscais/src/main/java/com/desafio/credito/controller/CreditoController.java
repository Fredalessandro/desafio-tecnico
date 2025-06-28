package com.desafio.credito.controller;

import com.desafio.credito.dto.CreditoDTO;
import com.desafio.credito.event.CreditoConsultaEvent;
import com.desafio.credito.event.EnumStatusConsulta;
import com.desafio.credito.event.EnunTipoConsulta;
import com.desafio.credito.exception.ErrorResponse;
import com.desafio.credito.service.CreditoEventPublisher;
import com.desafio.credito.service.CreditoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "Créditos", description = "Operações de consulta de créditos fiscais")
@RestController
@RequestMapping("/creditos")
@RequiredArgsConstructor
public class CreditoController {

  private final CreditoService creditoService;
  private final CreditoEventPublisher eventPublisher;
  private static final String ATRIBUTO_CONSULTA = "consulta_contexto";

  @Operation(summary = "Buscar créditos por NFS-e",
      description = "Retorna todos os créditos vinculados a uma NFS-e informada.\n\nExemplo de uso do JWT:\nAuthorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...",
      security = @SecurityRequirement(name = "bearerAuth"),
      parameters = {
          @Parameter(name = "numeroNfse", description = "Número da NFS-e a ser consultada",
              required = true, example = "123456789")},
      responses = {@ApiResponse(responseCode = "200", description = "Lista de créditos encontrados",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CreditoDTO.class),
              examples = @ExampleObject(
                  value = "[{\"numeroCredito\":\"CRED123\",\"numeroNfse\":\"123456789\",\"valorIssqn\":100.00}]"))),
          @ApiResponse(responseCode = "404", description = "Nenhum crédito encontrado para a NFS-e",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class),
                  examples = @ExampleObject(
                      value = "{\"message\":\"Nenhum crédito encontrado para a NFS-e: 123456789\"}"))),
          @ApiResponse(responseCode = "401", description = "Não autorizado")})
  @GetMapping("/{numeroNfse}")
  public ResponseEntity<List<CreditoDTO>> buscarCreditosPorNfse(@PathVariable String numeroNfse,
      HttpServletRequest request) {
    if (numeroNfse == null || numeroNfse.trim().isEmpty()) {
      throw new IllegalArgumentException("Número da NFS-e é obrigatório");
    }

    request.setAttribute(ATRIBUTO_CONSULTA, numeroNfse);
    List<CreditoDTO> creditos = creditoService.buscarCreditosPorNfse(numeroNfse);

    // Publicar evento de sucesso
    publicarEvento(numeroNfse, null, EnunTipoConsulta.POR_NFSE, creditos.size(),
        EnumStatusConsulta.SUCESSO, null, request);
    return ResponseEntity.ok(creditos);
  }

  @Operation(summary = "Buscar crédito por número",
      description = "Retorna um crédito pelo número do crédito.\n\nExemplo de uso do JWT:\nAuthorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...",
      security = @SecurityRequirement(name = "bearerAuth"),
      parameters = {
          @Parameter(name = "numeroCredito", description = "Número do crédito a ser consultado",
              required = true, example = "CRED123")},
      responses = {
          @ApiResponse(responseCode = "200", description = "Crédito encontrado", content = @Content(
              mediaType = "application/json", schema = @Schema(implementation = CreditoDTO.class),
              examples = @ExampleObject(
                  value = "{\"numeroCredito\":\"CRED123\",\"numeroNfse\":\"123456789\",\"valorIssqn\":100.00}"))),
          @ApiResponse(responseCode = "404", description = "Crédito não encontrado",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class),
                  examples = @ExampleObject(
                      value = "{\"message\":\"Crédito não encontrado com o número: CRED123\"}"))),
          @ApiResponse(responseCode = "401", description = "Não autorizado")})
  @GetMapping("/credito/{numeroCredito}")
  public ResponseEntity<CreditoDTO> buscarCreditoPorNumero(@PathVariable String numeroCredito,
      HttpServletRequest request) {
    if (numeroCredito == null || numeroCredito.trim().isEmpty()) {
      throw new IllegalArgumentException("Número do crédito é obrigatório");
    }

    request.setAttribute(ATRIBUTO_CONSULTA, numeroCredito);
    CreditoDTO credito = creditoService.buscarCreditoPorNumero(numeroCredito);

    // Publicar evento de sucesso
    publicarEvento(credito.getNumeroNfse(), numeroCredito, EnunTipoConsulta.POR_CREDITO, 1,
        EnumStatusConsulta.SUCESSO, null, request);
    return ResponseEntity.ok(credito);
  }

  @Operation(summary = "Status da API",
      description = "Endpoint de health check da API de Créditos.",
      responses = {@ApiResponse(responseCode = "200", description = "API de Créditos funcionando",
          content = @Content(mediaType = "text/plain",
              examples = @ExampleObject(value = "API de Créditos funcionando!")))})
  @GetMapping("/status")
  public ResponseEntity<String> status() {
    return ResponseEntity.ok("API de Créditos funcionando!");
  }

  private void publicarEvento(String numeroNfse, String numeroCredito, EnunTipoConsulta tipo,
      int resultados, EnumStatusConsulta status, String erro, HttpServletRequest request) {
    CreditoConsultaEvent evento = CreditoConsultaEvent.builder().numeroNfse(numeroNfse)
        .numeroCredito(numeroCredito).tipoConsulta(tipo).dataHoraConsulta(LocalDateTime.now())
        .quantidadeResultados(resultados).status(status).mensagemErro(erro)
        .usuario(obterUsuarioAtual(request)).ipOrigem(obterIpOrigem(request)).build();
    eventPublisher.publicarEventoAuditoria(evento);
  }

  private String obterUsuarioAtual(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()
        && !"anonymousUser".equals(authentication.getPrincipal())) {
      return authentication.getName();
    }
    return null;
  }

  private String obterIpOrigem(HttpServletRequest request) {
    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      return xForwardedFor.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }
}
