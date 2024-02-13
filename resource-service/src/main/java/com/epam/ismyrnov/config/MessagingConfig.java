package com.epam.ismyrnov.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class MessagingConfig {

  public static final String TOPIC = "topic-1";

  @Bean
  public NewTopic topic() {
    return TopicBuilder.name(TOPIC).build();
  }
}
