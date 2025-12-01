package com.example.kafkaprodport;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.core.out.ArticuloPublisher;
import com.example.domain.Articulo;

@Component
public class ArticuloKafkaPublisher implements ArticuloPublisher {

    public static final String TOPIC = "articulos";

    private final KafkaTemplate<String, Articulo> kafkaTemplate;

    ArticuloKafkaPublisher(KafkaTemplate<String, Articulo> template) {
        this.kafkaTemplate = template;
    }

    @Override
    public void publicarArticulo(Articulo articulo) {
        System.out.println("Publicando articulo: " + articulo.getNombre());

        this.kafkaTemplate.send(TOPIC, articulo);
    }
}
