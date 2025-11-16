package com.example.app;

import com.example.ddbbport.ArticlesDdbbEngine;
import com.example.core.HelloWorldService;
import com.example.core.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal Hello World modularizada
 * Demuestra el uso de JDK 21 con arquitectura modular
 */
@SpringBootApplication(scanBasePackages = "com.example")
public class OnlineShopAppRunner {
    
    private final HelloWorldService helloWorldService;
    
    public OnlineShopAppRunner() {
        this.helloWorldService = new HelloWorldService();
    }
    
    public static void main(String[] args) {
        OnlineShopAppRunner app = new OnlineShopAppRunner();
        app.run();
        SpringApplication.run(OnlineShopAppRunner.class, args);
    }
    
    /**
     * Ejecuta la aplicación principal
     */
    public void run() {
        System.out.println("=== Aplicación Tienda online Modular ===");
        System.out.println();
        
        // Mostrar mensaje principal
        System.out.println(this.helloWorldService.getGreetingMessage());
        
        // Mostrar información del sistema
        System.out.println(this.helloWorldService.getSystemInfo());
        
        // Mostrar información del entorno
        System.out.println(SystemUtils.getEnvironmentInfo());
        
        // Mostrar información de memoria
        System.out.println(SystemUtils.getMemoryInfo());
        
        // Demostrar características de Java 21
        System.out.println(this.helloWorldService.demonstrateJava21Features());
        
        // Mensaje de finalización
        System.out.println(this.helloWorldService.getCompletionMessage());

        System.out.println("=== Demostracion de conexion a la base de datos ===");
        System.out.println();

        // Demostrar conexión a la base de datos
        ArticlesDdbbEngine.ejemploConsultaSelect();
    }
}
