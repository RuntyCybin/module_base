package com.example.domain;

import java.sql.Timestamp;

public class Articulo {
    
    private Integer id;
    private String nombre;
    private Timestamp createdAt;
    private String descripcion;
    private double precio;
    private int stock;

    public Articulo() {
    }

    public Articulo(String nombre, String descripcion, double precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public Articulo(Integer id, String nombre, Timestamp createdAt, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.createdAt = createdAt;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
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
    
    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
