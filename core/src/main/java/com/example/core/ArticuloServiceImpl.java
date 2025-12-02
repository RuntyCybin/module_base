package com.example.core;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.core.in.ArticuloUseCase;
import com.example.ddbbport.ArticlesDdbbEngine;
import com.example.ddbbport.ArticuloMapper;
import com.example.domain.Articulo;
import com.example.domain.ArticuloRequest;

@Service
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticlesDdbbEngine articulosEngine;
    private final ArticuloMapper articuloMapper;
    private final ArticuloUseCase articuloUseCase;

    public ArticuloServiceImpl(ArticlesDdbbEngine articulosEngine,
            ArticuloMapper mapper, ArticuloUseCase useCase) {
        this.articulosEngine = articulosEngine;
        this.articuloMapper = mapper;
        this.articuloUseCase = useCase;
    }

    @Override
    public Articulo createArticle(ArticuloRequest articulo) {
        System.out.println("Creating article: " + articulo);

        Articulo auxArticulo = new Articulo(
            1, 
            "Nombre aux", 
            new Timestamp(System.currentTimeMillis()), 
            "descripcion articulo", 
            123, 
            12);

        this.articuloUseCase.crearArticulo(auxArticulo); 
        return auxArticulo;

        /*int rowsAdded = this.articulosEngine.crearArticulo(
                this.articuloMapper.toArticuloDdbb(articulo));

        if (rowsAdded == 0) {
            return null;
        } else {
            Articulo auxArticulo = this.articuloMapper.toArticuloFromArticuloRequest(articulo);
            // si se ha creado en la base de datos -> insertamos en el pipe de kafka
            this.articuloUseCase.crearArticulo(auxArticulo); 
            return this.articuloMapper.toArticuloFromArticuloRequest(articulo);
        }*/
    }

    @Override
    public List<Articulo> getArticles() {
        System.out.println("Getting articles");

        return this.articulosEngine.getArticulos().stream()
                .map(art -> articuloMapper.toArticulo(art))
                .toList();
    }

    @Override
    public Articulo getArticleById(String id) {
        System.out.println("Getting article by id: " + id);
        return null;
    }

    @Override
    public void updateArticle(Articulo articulo) {
        System.out.println("Updating article: " + articulo);
    }

    @Override
    public void deleteArticle(Articulo articulo) {
        System.out.println("Deleting article: " + articulo);
    }
}
