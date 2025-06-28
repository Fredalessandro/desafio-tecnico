package com.desafio.credito.exception;

import com.desafio.credito.event.CreditoConsultaEvent;
import com.desafio.credito.event.EnumStatusConsulta;
import com.desafio.credito.event.EnunTipoConsulta;
import com.desafio.credito.service.CreditoEventPublisher;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private final CreditoEventPublisher eventPublisher;

  public GlobalExceptionHandler(CreditoEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  // Adicionado
  public static class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(HttpStatus httpStatus, String message, String path) {
      this.timestamp = LocalDateTime.now();
      this.status = httpStatus.value();
      this.error = httpStatus.getReasonPhrase();
      this.message = message;
      this.path = path;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public int getStatus() {
      return status;
    }

    public String getError() {
      return error;
    }

    public String getMessage() {
      return message;
    }

    public String getPath() {
      return path;
    }
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
      WebRequest request) {
    String errorMessage = ex.getMessage();
    logger.warn("Recurso não encontrado: {} - Request: {}", errorMessage,
        request.getDescription(false));

    publishErrorEvent(request, EnumStatusConsulta.NAO_ENCONTRADO, errorMessage);

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, errorMessage,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex,
      WebRequest request) {
    String errorMessage = "Erro de validação: " + ex.getMessage();
    if (ex instanceof ConstraintViolationException) {
      errorMessage = ((ConstraintViolationException) ex).getConstraintViolations().stream()
          .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
          .collect(Collectors.joining(", "));
    }

    logger.warn("{} - Request: {}", errorMessage, request.getDescription(false));

    publishErrorEvent(request, EnumStatusConsulta.ERRO, errorMessage);

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
    logger.error("Erro inesperado na aplicação: Request: {}", request.getDescription(false), ex);
    String message = "Ocorreu um erro interno inesperado. Por favor, contate o suporte.";

    publishErrorEvent(request, EnumStatusConsulta.ERRO, message);

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, WebRequest request) {
    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.joining(", "));
    logger.warn("Erro de validação: {} - Request: {}", errorMessage, request.getDescription(false));
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
      WebRequest request) {
    logger.warn("IllegalArgumentException: {} - Request: {}", ex.getMessage(),
        request.getDescription(false));
    HttpStatus status = "Login já está em uso".equals(ex.getMessage()) ? HttpStatus.CONFLICT
        : HttpStatus.BAD_REQUEST;
    ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage(),
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(status).body(errorResponse);
  }

  private void publishErrorEvent(WebRequest request, EnumStatusConsulta status,
      String errorMessage) {
    String consultaContexto =
        (String) request.getAttribute("consulta_contexto", WebRequest.SCOPE_REQUEST);
    // Basic parsing to determine consultation type from URI
    String path = request.getDescription(false);
    EnunTipoConsulta tipoConsulta =
        path.contains("/credito/") ? EnunTipoConsulta.POR_CREDITO : EnunTipoConsulta.POR_NFSE;

    // Obter login do usuário autenticado
    String usuario = null;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()
        && !"anonymousUser".equals(authentication.getPrincipal())) {
      usuario = authentication.getName();
    }

    CreditoConsultaEvent evento = CreditoConsultaEvent.builder()
        .numeroNfse(tipoConsulta == EnunTipoConsulta.POR_NFSE ? consultaContexto : null)
        .numeroCredito(tipoConsulta == EnunTipoConsulta.POR_CREDITO ? consultaContexto : null)
        .tipoConsulta(tipoConsulta).dataHoraConsulta(LocalDateTime.now()).quantidadeResultados(0)
        .status(status).mensagemErro(errorMessage).usuario(usuario)
        .ipOrigem(request.getDescription(false)).build();
    eventPublisher.publicarEventoAuditoria(evento);
  }
}
