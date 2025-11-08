package com.example.app;

import com.example.core.HelloWorldService;
import com.example.core.SystemUtils;

/**
 * Aplicación principal Hello World modularizada
 * Demuestra el uso de JDK 21 con arquitectura modular
 */
public class OnlineShopAppRunner {
    
    private final HelloWorldService helloWorldService;
    
    public OnlineShopAppRunner() {
        this.helloWorldService = new HelloWorldService();
    }
    
    public static void main(String[] args) {
        OnlineShopAppRunner app = new OnlineShopAppRunner();
        app.run();
    }
    
    /**
     * Ejecuta la aplicación principal
     */
    public void run() {
        System.out.println("=== Aplicación Hello World Modularizada ===");
        System.out.println();
        
        // Mostrar mensaje principal
        System.out.println(helloWorldService.getGreetingMessage());
        
        // Mostrar información del sistema
        System.out.println(helloWorldService.getSystemInfo());
        
        // Mostrar información del entorno
        System.out.println(SystemUtils.getEnvironmentInfo());
        
        // Mostrar información de memoria
        System.out.println(SystemUtils.getMemoryInfo());
        
        // Demostrar características de Java 21
        System.out.println(helloWorldService.demonstrateJava21Features());
        
        // Mensaje de finalización
        System.out.println(helloWorldService.getCompletionMessage());
    }
}
