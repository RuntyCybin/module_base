package com.example.kafkaport;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.domain.Articulo;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    /*
     * 
     * Articulos ConsumerFactory y Listener
     * 
     */
    @Bean
    public ConsumerFactory<String, Articulo> articulosConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092"); // o via spring.kafka...
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.domain"); // paquete de Articulo
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(Articulo.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Articulo> articulosListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Articulo> factory = new ConcurrentKafkaListenerContainerFactory<String, Articulo>();
        factory.setConsumerFactory(articulosConsumerFactory());
        return factory;
    }

    /*
     * 
     * !Articulos ConsumerFactory y Listener
     * 
     */

}
