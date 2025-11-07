package com.example.app;

import com.example.core.HelloWorldService;
import com.example.core.SystemUtils;

/**
 * Clase para ejecutar la aplicación con diferentes configuraciones
 */
public class ApplicationRunner {
    
    private final HelloWorldService helloWorldService;
    
    public ApplicationRunner() {
        this.helloWorldService = new HelloWorldService();
    }
    
    /**
     * Ejecuta la aplicación en modo completo
     */
    public void runFullMode() {
        System.out.println("=== Modo Completo ===");
        System.out.println(helloWorldService.getGreetingMessage());
        System.out.println(helloWorldService.getSystemInfo());
        System.out.println(SystemUtils.getEnvironmentInfo());
        System.out.println(SystemUtils.getMemoryInfo());
        System.out.println(helloWorldService.demonstrateJava21Features());
        System.out.println(helloWorldService.getCompletionMessage());
    }
    
    /**
     * Ejecuta la aplicación en modo simple
     */
    public void runSimpleMode() {
        System.out.println("=== Modo Simple ===");
        System.out.println(helloWorldService.getGreetingMessage());
        System.out.println(helloWorldService.getCompletionMessage());
    }
    
    /**
     * Ejecuta la aplicación en modo de información del sistema
     */
    public void runSystemInfoMode() {
        System.out.println("=== Modo Información del Sistema ===");
        System.out.println(helloWorldService.getGreetingMessage());
        System.out.println(helloWorldService.getSystemInfo());
        System.out.println(SystemUtils.getEnvironmentInfo());
        System.out.println(SystemUtils.getMemoryInfo());
        System.out.println(helloWorldService.getCompletionMessage());
    }
    
    /**
     * Ejecuta la aplicación en modo de características de Java
     */
    public void runJavaFeaturesMode() {
        System.out.println("=== Modo Características de Java ===");
        System.out.println(helloWorldService.getGreetingMessage());
        System.out.println(helloWorldService.demonstrateJava21Features());
        System.out.println(helloWorldService.getCompletionMessage());
    }
}
