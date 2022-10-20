package com.smartshare.user_management.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Configuration
public class KafkaConfiguration {

  @Bean
  public KafkaAdmin admin() {
    var configs = new HashMap<String, Object>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic userTopic() {
    return TopicBuilder.name("all-types").partitions(4).replicas(1).compact().build();
  }
}
