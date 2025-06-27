package com.desafio.credito.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CreditoDTO {

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