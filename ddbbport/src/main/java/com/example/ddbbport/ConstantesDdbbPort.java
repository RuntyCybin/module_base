package com.example.ddbbport;

public abstract class ConstantesDdbbPort {
    public static final String DB_URL = System.getenv().getOrDefault("DATASOURCE_URL",
            "jdbc:postgresql://localhost:5432/tiendaonline");
    public static final String DB_USER = System.getenv().getOrDefault("DATASOURCE_USERNAME",
            "postgres");
    public static final String DB_PASSWORD = System.getenv().getOrDefault("DATASOURCE_PASSWORD",
            "postgres");
}
