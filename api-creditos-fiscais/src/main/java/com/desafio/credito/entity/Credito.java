package com.desafio.credito.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credito {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String numeroCredito;
  private String numeroNfse;
  private LocalDate dataConstituicao;
  private BigDecimal valorIssqn;
  private String tipoCredito;
  private Boolean simplesNacional;
  private BigDecimal aliquota;
  private BigDecimal valorFaturado;
  private BigDecimal valorDeducao;
  private BigDecimal baseCalculo;

}

