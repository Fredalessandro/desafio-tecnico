package com.desafio.credito.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  @Value("${spring.kafka.topic.credito-audit}")
  private String auditTopicName;

  @Value("${app.topic.partitions:1}")
  private int partitions;

  @Value("${app.topic.replication-factor:1}")
  private short replicationFactor;

  @Bean
  public NewTopic auditTopic() {
    return TopicBuilder.name(auditTopicName).partitions(partitions).replicas(replicationFactor)
        .build();
  }
}
