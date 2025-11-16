package com.example.ddbbport;

import java.sql.Timestamp;

public class ArticuloDdbb {
    private Integer id;
    private String nombre;
    private Timestamp createdAt;
    private String descripcion;
    private double precio;
    private int stock;

    public ArticuloDdbb(Integer id, String nombre, Timestamp createdAt, int precio, String descripcion, int stock) {
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

    public Timestamp getCreatedAt() {
        return createdAt;
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
}
