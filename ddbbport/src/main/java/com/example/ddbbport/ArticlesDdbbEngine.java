package com.example.ddbbport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArticlesDdbbEngine {

  private static final String DB_URL = System.getenv().getOrDefault("DATASOURCE_URL",
          "jdbc:postgresql://localhost:5432/tiendaonline");
  private static final String DB_USER = System.getenv().getOrDefault("DATASOURCE_USERNAME",
          "postgres");
  private static final String DB_PASSWORD = System.getenv().getOrDefault("DATASOURCE_PASSWORD",
          "postgres");


  public void ejemploDatosArticulos() {
    System.out.println("=== Ejemplo de datos de artículos ===");

    try (final Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      System.out.println("✅ Conexión establecida exitosamente!");

      try (final Statement stmt = connection.createStatement();
           final ResultSet rs = stmt.executeQuery("SELECT * FROM articulos")) {
        while (rs.next()) {
          System.out.println("ID: " + rs.getInt("id"));
          System.out.println("Articulo: " + rs.getString("titulo_art"));
          System.out.println("Fecha de creación: " + rs.getString("createdat"));
        }
      }

      System.out.println("✅ Datos de artículos obtenidos exitosamente!");

    } catch (final SQLException e) {
      System.err.println("❌ Error al conectar: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Ejemplo 1: Conexión simple con try-with-resources
   * La conexión se cierra automáticamente al salir del bloque try
   */
  public static void ejemploConexionSimple() {
    System.out.println("=== Ejemplo 1: Conexión Simple ===");

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      System.out.println("✅ Conexión establecida exitosamente!");

      // Verificar la versión de PostgreSQL
      try (Statement stmt = connection.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT version()")) {

        if (rs.next()) {
          System.out.println("Versión de PostgreSQL: " + rs.getString(1));
        }
      }

    } catch (SQLException e) {
      System.err.println("❌ Error al conectar: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Ejemplo 2: Consulta SELECT con try-with-resources
   * Tanto Connection, Statement como ResultSet se cierran automáticamente
   */
  public static void ejemploConsultaSelect() {
    System.out.println("\n=== Ejemplo 2: Consulta SELECT ===");

    String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' LIMIT 5";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

      System.out.println("Tablas en la base de datos:");
      while (rs.next()) {
        System.out.println("  - " + rs.getString("table_name"));
      }

    } catch (SQLException e) {
      System.err.println("❌ Error en la consulta: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Ejemplo 3: INSERT con PreparedStatement y try-with-resources
   * Usa PreparedStatement para prevenir SQL injection
   */
  public static void ejemploInsert() {
    System.out.println("\n=== Ejemplo 3: INSERT con PreparedStatement ===");

    String insertQuery = """
            INSERT INTO articulos (nombre, precio, categoria_id)
            VALUES (?, ?, ?)
            """;

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {

      // Establecer parámetros
      pstmt.setString(1, "Producto Ejemplo");
      pstmt.setDouble(2, 29.99);
      pstmt.setInt(3, 1);

      int filasAfectadas = pstmt.executeUpdate();
      System.out.println("✅ Filas insertadas: " + filasAfectadas);

    } catch (SQLException e) {
      System.err.println("❌ Error al insertar: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Ejemplo 4: UPDATE con transacción y try-with-resources
   * Demuestra el uso de transacciones
   */
  public static void ejemploUpdateConTransaccion() {
    System.out.println("\n=== Ejemplo 4: UPDATE con Transacción ===");

    String updateQuery = "UPDATE articulos SET precio = ? WHERE id = ?";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      // Desactivar auto-commit para controlar la transacción
      connection.setAutoCommit(false);

      try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
        pstmt.setDouble(1, 39.99);
        pstmt.setInt(2, 1);

        int filasAfectadas = pstmt.executeUpdate();
        System.out.println("✅ Filas actualizadas: " + filasAfectadas);

        // Confirmar la transacción
        connection.commit();
        System.out.println("✅ Transacción confirmada");

      } catch (SQLException e) {
        // Revertir la transacción en caso de error
        connection.rollback();
        System.err.println("❌ Error, transacción revertida: " + e.getMessage());
        throw e;
      } finally {
        // Restaurar auto-commit
        connection.setAutoCommit(true);
      }

    } catch (SQLException e) {
      System.err.println("❌ Error en la transacción: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Ejemplo 5: Consulta con PreparedStatement y parámetros
   */
  public static void ejemploConsultaConParametros(int categoriaId) {
    System.out.println("\n=== Ejemplo 5: Consulta con Parámetros ===");

    String query = "SELECT id, nombre, precio FROM articulos WHERE categoria_id = ?";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement pstmt = connection.prepareStatement(query)) {

      pstmt.setInt(1, categoriaId);

      try (ResultSet rs = pstmt.executeQuery()) {
        System.out.println("Artículos de la categoría " + categoriaId + ":");
        while (rs.next()) {
          int id = rs.getInt("id");
          String nombre = rs.getString("nombre");
          double precio = rs.getDouble("precio");
          System.out.printf("  ID: %d, Nombre: %s, Precio: %.2f%n", id, nombre, precio);
        }
      }

    } catch (SQLException e) {
      System.err.println("❌ Error en la consulta: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Ejemplo 6: Múltiples recursos en un solo try-with-resources
   * Todos los recursos se cierran automáticamente en orden inverso
   */
  public static void ejemploMultiplesRecursos() {
    System.out.println("\n=== Ejemplo 6: Múltiples Recursos ===");

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT current_database(), current_user")) {

      if (rs.next()) {
        System.out.println("Base de datos: " + rs.getString(1));
        System.out.println("Usuario: " + rs.getString(2));
      }

    } catch (SQLException e) {
      System.err.println("❌ Error: " + e.getMessage());
      e.printStackTrace();
    }
    // Todos los recursos (rs, stmt, conn) se cierran automáticamente aquí
  }

}