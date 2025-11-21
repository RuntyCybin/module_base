package com.example.core;

import java.util.List;

import com.example.domain.Articulo;
import com.example.domain.ArticuloRequest;

public interface ArticuloService {
    public Articulo createArticle(ArticuloRequest articulo);
    public List<Articulo> getArticles();
    public Articulo getArticleById(String id);
    public void updateArticle(Articulo articulo);
    public void deleteArticle(Articulo articulo);
}
