package com.desafio.credito.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Resposta de erro padrão")
public class ErrorResponse {
  @Schema(description = "Data e hora do erro", example = "2024-06-25T20:00:00")
  private LocalDateTime timestamp;
  @Schema(description = "Código HTTP", example = "404")
  private int status;
  @Schema(description = "Tipo do erro", example = "Not Found")
  private String error;
  @Schema(description = "Mensagem de erro",
      example = "Crédito não encontrado com o número: CRED123")
  private String message;
  @Schema(description = "Path da requisição", example = "/api/creditos/credito/CRED123")
  private String path;

  public ErrorResponse() {}

  public ErrorResponse(LocalDateTime timestamp, int status, String error, String message,
      String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
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

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
