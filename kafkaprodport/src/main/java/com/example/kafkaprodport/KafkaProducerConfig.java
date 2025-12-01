package com.example.kafkaprodport;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.domain.Articulo;

@Configuration
public class KafkaProducerConfig {

    /*
     * 
     * Articulos ProducerFactory y Template
     * 
     */
    @Bean
    public ProducerFactory<String, Articulo> articuloProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
    }

    @Bean
    public KafkaTemplate<String, Articulo> articuloKafkaTemplate() {
        return new KafkaTemplate<>(articuloProducerFactory());
    }
    /*
     * 
     * !Articulos ProducerFactory y Template
     * 
     */

}
