package com.desafio.credito.exception;

import com.desafio.credito.service.CreditoEventPublisher;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;
  private WebRequest webRequest;
  private CreditoEventPublisher eventPublisher;

  @BeforeEach
  void setUp() {
    eventPublisher = mock(CreditoEventPublisher.class);
    handler = new GlobalExceptionHandler(eventPublisher);
    webRequest = mock(WebRequest.class);
    when(webRequest.getDescription(false)).thenReturn("uri=/api/creditos/teste");
  }

  @Test
  void handleResourceNotFoundException_deveRetornar404() {
    ResourceNotFoundException ex = new ResourceNotFoundException("Não encontrado");
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        handler.handleResourceNotFoundException(ex, webRequest);
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("Não encontrado", response.getBody().getMessage());
  }

  @Test
  void handleConstraintViolationException_deveRetornar400() {
    ConstraintViolation<?> violation = mock(ConstraintViolation.class);
    jakarta.validation.Path mockPath = mock(jakarta.validation.Path.class);
    when(mockPath.toString()).thenReturn("campo");
    when(violation.getPropertyPath()).thenReturn(mockPath);
    when(violation.getMessage()).thenReturn("não pode ser vazio");
    Set<ConstraintViolation<?>> violations = Collections.singleton(violation);
    ConstraintViolationException ex = new ConstraintViolationException(violations);
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        handler.handleValidationExceptions(ex, webRequest);
    assertEquals(400, response.getStatusCodeValue());
    assertTrue(response.getBody().getMessage().contains("campo: não pode ser vazio"));
  }

  @Test
  void handleGlobalException_deveRetornar500() {
    Exception ex = new Exception("Erro inesperado");
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
        handler.handleGlobalException(ex, webRequest);
    assertEquals(500, response.getStatusCodeValue());
    assertTrue(response.getBody().getMessage().contains("Ocorreu um erro interno"));
  }
}
