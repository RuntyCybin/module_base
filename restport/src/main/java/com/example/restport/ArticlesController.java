package com.example.restport;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.ArticuloService;
import com.example.domain.Articulo;
import com.example.domain.ArticuloRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

  private final ArticuloService servicio;
  private final Sinks.Many<Articulo> articulosSink;

  ArticlesController(ArticuloService service, Sinks.Many<Articulo> articulosSink) {
    this.servicio = service;
    this.articulosSink = articulosSink;
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

  @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Articulo> getArticulosSSE() {
    return this.articulosSink.asFlux();
  }
}