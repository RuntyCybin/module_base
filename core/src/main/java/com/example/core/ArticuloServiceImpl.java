package com.example.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ddbbport.ArticlesDdbbEngine;
import com.example.ddbbport.ArticuloMapper;
import com.example.domain.Articulo;

@Service
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticlesDdbbEngine articulosEngine;
    private final ArticuloMapper articuloMapper;

    public ArticuloServiceImpl(ArticlesDdbbEngine articulosEngine, ArticuloMapper mapper) {
        this.articulosEngine = articulosEngine;
        this.articuloMapper = mapper;
    }

    @Override
    public void createArticle(Articulo articulo) {
        System.out.println("Creating article: " + articulo);
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
