package com.example.restport;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

  @PostMapping("/create")
  public ResponseEntity<String> createArticle(@RequestBody String valor) {
    System.out.println("Article " + valor + " created");
    return new ResponseEntity<>(valor, HttpStatus.CREATED);
  }

}