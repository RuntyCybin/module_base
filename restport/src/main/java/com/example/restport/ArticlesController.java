package com.example.restport;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.ArticuloService;
import com.example.domain.Articulo;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

  private final ArticuloService servicio;

  ArticlesController(ArticuloService service) {
    this.servicio = service;
  }

  @PostMapping("/create")
  public ResponseEntity<List<Articulo>> createArticle(@RequestBody String valor) {
    System.out.println("Article " + valor + " created");

    List<Articulo> result = servicio.getArticles();

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

}