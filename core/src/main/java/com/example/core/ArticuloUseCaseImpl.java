package com.example.core;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.core.in.ArticuloUseCase;
import com.example.core.out.ArticuloPublisher;
import com.example.domain.Articulo;

@Service
public class ArticuloUseCaseImpl implements ArticuloUseCase {

    private final ArticuloPublisher publisher;

    public ArticuloUseCaseImpl(ArticuloPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void crearArticulo(Articulo articulo) {
        // Business logic for creating an article can be added here
        Optional.ofNullable(articulo)
            .ifPresent(publisher::publicarArticulo);
    }
    
}
