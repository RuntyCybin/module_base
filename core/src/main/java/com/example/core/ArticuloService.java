package com.example.core;

import java.util.List;

import com.example.domain.Articulo;

public interface ArticuloService {
    public void createArticle(Articulo articulo);
    public List<Articulo> getArticles();
    public Articulo getArticleById(String id);
    public void updateArticle(Articulo articulo);
    public void deleteArticle(Articulo articulo);
}
