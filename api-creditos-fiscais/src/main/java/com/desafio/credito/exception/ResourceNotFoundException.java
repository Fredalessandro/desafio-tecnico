package com.desafio.credito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// A anotação @ResponseStatus é opcional aqui, pois o GlobalExceptionHandler cuidará disso,
// mas pode ser útil para clareza ou se a exceção vazar sem ser pega pelo handler.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
