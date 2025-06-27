package com.desafio.credito.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, errorMessage,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class})
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    String validationErrorMessage = ex.getConstraintViolations().stream()
        .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage()).collect(Collectors.joining(", "));
    String detailedMessage = "Erro de validação: " + validationErrorMessage;
    logger.warn("{} - Request: {}", detailedMessage, request.getDescription(false));
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, detailedMessage,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
    logger.error("Erro inesperado na aplicação: Request: {}", request.getDescription(false), ex);
    String message = "Ocorreu um erro interno inesperado. Por favor, contate o suporte.";
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message,
        request.getDescription(false).replace("uri=", ""));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
