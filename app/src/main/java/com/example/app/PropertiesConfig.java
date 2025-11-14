package com.example.app;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class PropertiesConfig {
  private static final Properties properties = new Properties();

  static {
    System.out.println("=== CARGAMOS LAS PROPIEDADES ===");
    loadProperties();
  }

  private static void loadProperties() {
    try (final InputStream inputStream = PropertiesConfig.class.getClassLoader()
            .getResourceAsStream("application.yml")) {

      if (inputStream == null) {
        throw new IllegalStateException("application.yml no encontrado");
      }

      final Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
      final var ymlContent = scanner.hasNext() ? scanner.next() : "";
      scanner.close();

      final var yamlMap = parseYaml(ymlContent);
      final var flatMap = flatten(yamlMap,"");

      properties.putAll(flatMap);

    } catch (Exception e) {
      throw new RuntimeException("Error cargando application.yml", e);
    }
  }

  // Parser YAML muy básico (solo para este ejemplo)
  private static Map<String, Object> parseYaml(String content) {
    Map<String, Object> map = new HashMap<>();
    String[] lines = content.split("\n");
    String currentPath = "";

    for (String line : lines) {
      line = line.trim();
      if (line.isEmpty() || line.startsWith("#")) continue;

      int indent = line.length() - line.replaceAll("^\\s+", "").length();
      String trimmed = line.strip();

      if (trimmed.contains(":")) {
        String[] parts = trimmed.split(":", 2);
        String key = parts[0].trim();
        String value = parts.length > 1 ? parts[1].trim() : "";

        // Quitar comillas si existen
        if (value.startsWith("\"") && value.endsWith("\"")) {
          value = value.substring(1, value.length() - 1);
        }

        currentPath = currentPath.isEmpty() ? key : currentPath + "." + key;
        map.put(currentPath, value);
      }
    }
    return map;
  }

  // Aplanar: {app.database.url -> jdbc:...}
  private static Map<String, String> flatten(Map<String, Object> map, String prefix) {
    Map<String, String> flat = new HashMap<>();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
      Object value = entry.getValue();
      if (value instanceof String str) {
        flat.put(key, str);
      }
    }
    return flat;
  }

  // Métodos públicos
  public static String get(String key) {
    return properties.getProperty(key);
  }

  public static String get(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }
}
