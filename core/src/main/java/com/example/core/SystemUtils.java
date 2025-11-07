package com.example.core;

/**
 * Utilidades del sistema
 */
public class SystemUtils {
    
    /**
     * Verifica si el sistema está ejecutándose en un contenedor Docker
     * @return true si está en Docker, false en caso contrario
     */
    public static boolean isRunningInDocker() {
        try {
            // Verificar si existe el archivo .dockerenv
            return java.nio.file.Files.exists(java.nio.file.Paths.get("/.dockerenv"));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene información detallada del entorno de ejecución
     * @return información del entorno
     */
    public static String getEnvironmentInfo() {
        StringBuilder env = new StringBuilder();
        env.append("--- Información del Entorno ---\n");
        env.append("Ejecutándose en Docker: ").append(isRunningInDocker() ? "Sí" : "No").append("\n");
        env.append("Directorio de trabajo: ").append(System.getProperty("user.dir")).append("\n");
        env.append("Usuario: ").append(System.getProperty("user.name")).append("\n");
        env.append("Arquitectura: ").append(System.getProperty("os.arch")).append("\n");
        return env.toString();
    }
    
    /**
     * Obtiene información de memoria del sistema
     * @return información de memoria
     */
    public static String getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        StringBuilder memory = new StringBuilder();
        memory.append("--- Información de Memoria ---\n");
        memory.append("Memoria máxima: ").append(formatBytes(maxMemory)).append("\n");
        memory.append("Memoria total: ").append(formatBytes(totalMemory)).append("\n");
        memory.append("Memoria usada: ").append(formatBytes(usedMemory)).append("\n");
        memory.append("Memoria libre: ").append(formatBytes(freeMemory)).append("\n");
        return memory.toString();
    }
    
    /**
     * Formatea bytes en una representación legible
     * @param bytes número de bytes
     * @return string formateado
     */
    private static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
