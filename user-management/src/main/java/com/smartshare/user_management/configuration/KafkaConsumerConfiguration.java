package com.smartshare.user_management.configuration;

import com.smartshare.user_management.model.AllTypes;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

  @Bean
  public Map<String, Object> consumerConfigs() {
    var properties = new HashMap<String, Object>();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    properties.put(
        ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,
        "org.apache.kafka.clients.consumer.RoundRobinAssignor");
    properties.put(
        AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
    properties.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, false);
    properties.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true);
    properties.put(AbstractKafkaSchemaSerDeConfig.USE_SCHEMA_ID, 3);
    properties.put("specific.avro.reader", true);
    return properties;
  }

  @Bean
  public ConsumerFactory<Integer, AllTypes> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, AllTypes>>
      kafkaListenerContainerFactory() {
    var kafkaListenerContainerFactory =
        new ConcurrentKafkaListenerContainerFactory<Integer, AllTypes>();
    kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
    kafkaListenerContainerFactory.setConcurrency(3);
    kafkaListenerContainerFactory.getContainerProperties().setPollTimeout(3000);
    return kafkaListenerContainerFactory;
  }
}
