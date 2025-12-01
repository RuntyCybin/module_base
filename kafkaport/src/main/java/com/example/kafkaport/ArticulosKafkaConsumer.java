package com.example.kafkaport;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.domain.Articulo;

@Component
public class ArticulosKafkaConsumer {

    public static final String TOPIC = "articulos";
    public static final String GROUP = "grupo_articulos";

    @KafkaListener(topics = TOPIC, groupId = GROUP, containerFactory = "articulosListenerFactory")
    public void onReceiveArticulo(Articulo articulo) {
        System.out.println("-------------------------------------------");
        System.out.println("ARTICULO RECIBIDO: " + articulo.getNombre());
        System.out.println("-------------------------------------------");
    }
    
}
