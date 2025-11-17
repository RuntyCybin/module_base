package com.example.domain;

public class CategoriaArticulo {
    private Integer id;
    private String nombre;
    private String descripcion;

    public CategoriaArticulo(Integer id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
