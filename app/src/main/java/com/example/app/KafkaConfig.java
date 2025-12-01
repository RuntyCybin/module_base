package com.example.app;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic articulosTopic() {
        return new NewTopic("articulos", 1, (short) 1);
    }
    
}
