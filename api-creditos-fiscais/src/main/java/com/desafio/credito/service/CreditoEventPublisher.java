package com.desafio.credito.service;

import com.desafio.credito.event.CreditoConsultaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditoEventPublisher {

  private final KafkaTemplate<String, CreditoConsultaEvent> kafkaTemplate;

  @Value("${spring.kafka.topic.credito-audit}")
  private String auditTopicName;

  public void publicarEventoAuditoria(CreditoConsultaEvent evento) {
    try {
      CompletableFuture<SendResult<String, CreditoConsultaEvent>> future =
          kafkaTemplate.send(auditTopicName, evento.getNumeroNfse(), evento);

      future.whenComplete((result, exception) -> {
        if (exception == null) {
          log.info("Evento de auditoria publicado com sucesso: key={}, offset={}",
              evento.getNumeroNfse(), result.getRecordMetadata().offset());
        } else {
          log.error("Falha ao publicar evento de auditoria: key={}", evento.getNumeroNfse(),
              exception);
        }
      });
    } catch (Exception e) {
      log.error("Erro inesperado ao publicar evento de auditoria", e);
    }
  }
}
