package com.epam.ismyrnov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import feign.Retryer;

@Configuration
@EnableRetry
public class FeignClientConfig {

  @Bean
  public Retryer feignRetryer() {
    return new Retryer.Default(
        100, 1000, 2); // 2 retries with a backoff period of 1000 milliseconds
  }
}
