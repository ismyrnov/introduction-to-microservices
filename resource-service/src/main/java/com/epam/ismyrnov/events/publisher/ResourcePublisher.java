package com.epam.ismyrnov.events.publisher;

import static com.epam.ismyrnov.config.MessagingConfig.TOPIC;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourcePublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void publish(String message) {
    log.info("-- Publisher is sending message: '{}'", message);
    try {
      kafkaTemplate.send(TOPIC, message);
    } catch (KafkaException ex) {
      log.error("Publishing error: ", ex);
      return;
    }
    log.info("-- Successfully Publisher's sent the message");
  }
}
