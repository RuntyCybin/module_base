package com.example.kafkaport;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.domain.Articulo;

import reactor.core.publisher.Sinks;

@Component
public class ArticulosKafkaConsumer {

    private final Sinks.Many<Articulo> articulosSink;

    ArticulosKafkaConsumer(Sinks.Many<Articulo> articulosSink) {
        this.articulosSink = articulosSink;
    }

    public static final String TOPIC = "articulos";
    public static final String GROUP = "articulos-group-dev";

    @KafkaListener(topics = TOPIC, groupId = GROUP, containerFactory = "articulosListenerFactory")
    public void onReceiveArticulo(Articulo articulo) {
        System.out.println("-------------------------------------------");
        System.out.println("ARTICULO RECIBIDO: " + articulo.getNombre());
        System.out.println("-------------------------------------------");

        // Emitimos el artículo al stream SSE (Server-Sent Events)
        this.articulosSink.tryEmitNext(articulo);
    }

}
