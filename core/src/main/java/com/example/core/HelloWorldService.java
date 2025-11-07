package com.example.core;

/**
 * Servicio principal que contiene la lógica de negocio
 * para la aplicación Hello World
 */
public class HelloWorldService {
    
    /**
     * Genera el mensaje principal de saludo
     * @return mensaje de saludo
     */
    public String getGreetingMessage() {
        return "¡Hola Mundo desde Java 21!";
    }
    
    /**
     * Obtiene información del sistema
     * @return información detallada del sistema
     */
    public String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Versión de Java: ").append(System.getProperty("java.version")).append("\n");
        info.append("Vendor: ").append(System.getProperty("java.vendor")).append("\n");
        info.append("OS: ").append(System.getProperty("os.name"))
            .append(" ").append(System.getProperty("os.version"));
        return info.toString();
    }
    
    /**
     * Demuestra características modernas de Java 21
     * @return información sobre características de Java
     */
    public String demonstrateJava21Features() {
        StringBuilder features = new StringBuilder();
        features.append("\n--- Características de Java 21 ---\n");
        
        // Text Blocks (desde Java 15)
        String message = """
            Este es un ejemplo de Text Block
            que permite escribir texto multilínea
            de forma más legible.
            """;
        features.append(message);
        
        // Pattern Matching for instanceof (desde Java 16)
        Object obj = "Hola desde Pattern Matching";
        if (obj instanceof String str) {
            features.append("Longitud del string: ").append(str.length()).append("\n");
        }
        
        // Switch Expressions (desde Java 14)
        String day = "lunes";
        String dayType = switch (day) {
            case "lunes", "martes", "miércoles", "jueves", "viernes" -> "día laboral";
            case "sábado", "domingo" -> "fin de semana";
            default -> "día desconocido";
        };
        features.append(day).append(" es un ").append(dayType).append("\n");
        
        return features.toString();
    }
    
    /**
     * Genera el mensaje de finalización
     * @return mensaje de finalización
     */
    public String getCompletionMessage() {
        return "\n¡Aplicación ejecutada exitosamente!";
    }
}
