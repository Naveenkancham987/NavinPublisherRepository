package com.pk.publisher.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.pk.publisher.model.Customer;

/**
 * 
 * @author navi
 *
 */

@Configuration
public class PublisherKakfaConfiguration {

  private static final Logger log = LoggerFactory.getLogger(PublisherKakfaConfiguration.class);

  @Value("${kafka.hostname}")
  private String hostName;

  /**
   * 
   * @return ProducerFactory
   */
  @Bean
  public ProducerFactory<String, Customer> producerFactory() {
    log.info("In PublisherKakfaConfiguration class");
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hostName);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
  }

  /**
   * 
   * @return KafkaTemplate
   */

  @Bean
  public KafkaTemplate<String, Customer> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }


}
