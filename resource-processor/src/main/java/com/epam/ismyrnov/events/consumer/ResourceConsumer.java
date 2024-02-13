package com.epam.ismyrnov.events.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.ismyrnov.service.ResourceProcessorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceConsumer {
  public static final String TOPIC = "topic-1";

  private final ResourceProcessorService resourceProcessorService;

  @KafkaListener(id = "my1-id-1", topics = TOPIC)
  public void consume(Integer id) {
    log.info("-Consumer's got a message: '{}'", id);

    resourceProcessorService.upload(id);
  }
}
