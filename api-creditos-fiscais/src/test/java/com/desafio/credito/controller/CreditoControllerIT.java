package com.desafio.credito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CreditoControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void buscarCreditosPorNfse_deveRetornarCreditosParaNfseExistente() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/api/creditos/7891011").contentType(MediaType.APPLICATION_JSON));
    result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
        .andExpect(jsonPath("$[0].numeroNfse", is("7891011")));
  }

  @Test
  void buscarCreditosPorNfse_deveRetornar404ParaNfseInexistente() throws Exception {
    mockMvc.perform(get("/api/creditos/0000000").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void buscarCreditoPorNumero_deveRetornarCreditoParaNumeroExistente() throws Exception {
    mockMvc.perform(get("/api/creditos/credito/123456").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.numeroCredito", is("123456")))
        .andExpect(jsonPath("$.numeroNfse", is("7891011")));
  }

  @Test
  void buscarCreditoPorNumero_deveRetornar404ParaNumeroInexistente() throws Exception {
    mockMvc.perform(get("/api/creditos/credito/999999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void status_deveRetornarOk() throws Exception {
    mockMvc.perform(get("/api/creditos/status")).andExpect(status().isOk())
        .andExpect(content().string(containsString("API de Créditos funcionando!")));
  }
}
