package com.example.restport;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.ArticuloService;
import com.example.domain.Articulo;
import com.example.domain.ArticuloRequest;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

  private final ArticuloService servicio;

  ArticlesController(ArticuloService service) {
    this.servicio = service;
  }
  
  @GetMapping
  public ResponseEntity<List<Articulo>> getArticles() {
    System.out.println("Recuperando todos los articulos");
    return new ResponseEntity<>(
      this.servicio.getArticles(),
      HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Articulo> crearArticulo(@RequestBody ArticuloRequest request) {
    System.out.println("Creando articulo");
    return new ResponseEntity<>(
      this.servicio.createArticle(request),
      HttpStatus.CREATED);
  }

}