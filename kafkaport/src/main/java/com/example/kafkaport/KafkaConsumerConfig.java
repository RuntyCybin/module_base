package com.example.kafkaport;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.domain.Articulo;
import reactor.core.publisher.Sinks;

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
        JsonDeserializer<Articulo> jsonDeserializer = new JsonDeserializer<>(Articulo.class);
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "articulos-group-dev",
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                        ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class),
                new StringDeserializer(),
                jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Articulo> articulosListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Articulo> factory = new ConcurrentKafkaListenerContainerFactory<String, Articulo>();
        factory.setConsumerFactory(articulosConsumerFactory());
        factory.setAutoStartup(true);
        return factory;
    }

    /*
     * 
     * !Articulos ConsumerFactory y Listener
     * 
     */

    @Bean
    public Sinks.Many<Articulo> articulosSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
