package com.desafio.credito.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditoConsultaEvent {
  private String numeroNfse;
  private String numeroCredito;
  private EnunTipoConsulta tipoConsulta;
  private String usuario;
  private String ipOrigem;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHoraConsulta;

  private Integer quantidadeResultados;
  private EnumStatusConsulta status;
  private String mensagemErro;
}
