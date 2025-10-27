package com.larian.aerolineas;

import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aerolineas { 
      // --- Credenciales de la BD ---
      private static final String url = "jdbc:mysql://localhost:3306/AEROLINEAS";
      private static final String user = "root";
      private static final String password = "root";
    
    public static void main(String[] args) throws SQLException {
      try (Connection cn = getConnection(url, user, password)) {
        // Mensaje de conexión exitosa
        System.out.println("\nConectado a la base de datos.\n");
        
        // Apartado 1
        mostrarTodosLosDatos(cn); // Se pasa cn como atributo
        
        // Apartado 2
        mostrarPasajeros(cn, "IB-SP-4567");
        
        // Apartado 3
        insertarVuelo(cn, "PRUEBA3", "PRUEBA", "PRUEBA", "PRUEBA", 1, 2, 3, 4);
        
        // Apartado 4
        eliminarVuelo(cn, "PRUEBA2");
        
        // Apartado 5
        actualizarPlazasFumadores(cn);
        
        // Apartado 6
        insertarDatosPasajeros(cn);
        
      } catch (SQLException e) {
        System.out.println("\n[!]Error: \n" + e + "\n");
      }
    }
    
    // --- Apartado 1 ---
    public static void mostrarTodosLosDatos(Connection cn) throws SQLException {
      // Sentencia SQL
      String sql = "SELECT * FROM PASAJEROS INNER JOIN VUELOS ON VUELOS.COD_VUELO = PASAJEROS.COD_VUELO;";
      // Try con recursos
      try (PreparedStatement ps = cn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        // Mostrar información
        System.out.println("\nTodos los datos:\n");
        System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s%n",
            "COD_VUELO",
            "HORA_SALIDA",
            "DESTINO",
            "PROCEDENCIA",
            "PLAZAS_FUMADOR",
            "PLAZAS_NO_FUMADOR",
            "PLAZAS_TURISTA",
            "PLAZAS_PRIMERA"
        );
        while(rs.next()) {          
          System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s%n",
              rs.getString("COD_VUELO"),
              rs.getString("HORA_SALIDA"),
              rs.getString("DESTINO"),
              rs.getString("PROCEDENCIA"),
              rs.getString("PLAZAS_FUMADOR"),
              rs.getString("PLAZAS_NO_FUMADOR"),
              rs.getString("PLAZAS_TURISTA"),
              rs.getString("PLAZAS_PRIMERA")
          );
        } // Catch del main lo gestiona
      }
    }
    
    // --- Apartado 2 ---
    public static void mostrarPasajeros(Connection cn, String codVuelo) throws SQLException {
      // Sentencia SQL
      String sql = "SELECT * FROM PASAJEROS WHERE COD_VUELO = ?;";
      // Try con recursos
      try (PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, codVuelo);
        ResultSet rs = ps.executeQuery();
        // Mostrar información
        System.out.println("\nPasajeros con código de vuelo " + codVuelo + ":\n");
        System.out.printf("%-18s %-18s %-18s %-18s%n",
            "NUM",
            "COD_VUELO",
            "TIPO_PLAZA",
            "FUMADOR"
        );
        while(rs.next()) {
          System.out.printf("%-18s %-18s %-18s %-18s%n",
              rs.getString("NUM"),
              rs.getString("COD_VUELO"),
              rs.getString("TIPO_PLAZA"),
              rs.getString("FUMADOR")
          );
        } // Catch del main lo gestiona
      }      
    }
    
    // --- Apartado 3 ---
    public static void insertarVuelo(
            Connection cn,
            String codVuelo,
            String horaSalida,
            String destino,
            String procedencia,
            int plazasFumadores,
            int plazasNoFumadores,
            int plazasTur, 
            int plazasPri
    ) throws SQLException {
      // Sentencia SQL
      String sql = "INSERT INTO VUELOS VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
      try (PreparedStatement ps = cn.prepareStatement(sql)){
        ps.setString(1, codVuelo);
        ps.setString(2, horaSalida);
        ps.setString(3, destino);
        ps.setString(4, procedencia);
        ps.setInt(5, plazasFumadores);
        ps.setInt(6, plazasNoFumadores);
        ps.setInt(7, plazasTur);
        ps.setInt(8, plazasPri);
        ps.executeUpdate();
      }
      System.out.println("\nVuelos insertados.");
      // Sentencia SQL
      sql = "SELECT * FROM VUELOS;";
      try (PreparedStatement ps = cn.prepareStatement(sql)){
        ResultSet rs = ps.executeQuery();
        // Mostrar resultados
        System.out.println("Resultados:\n");
        System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s%n",
            "COD_VUELO",
            "HORA_SALIDA",
            "DESTINO",
            "PROCEDENCIA",
            "PLAZAS_FUMADOR",
            "PLAZAS_NO_FUMADOR",
            "PLAZAS_TURISTA",
            "PLAZAS_PRIMERA"
        );
        while(rs.next()) {          
          System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s%n",
              rs.getString("COD_VUELO"),
              rs.getString("HORA_SALIDA"),
              rs.getString("DESTINO"),
              rs.getString("PROCEDENCIA"),
              rs.getString("PLAZAS_FUMADOR"),
              rs.getString("PLAZAS_NO_FUMADOR"),
              rs.getString("PLAZAS_TURISTA"),
              rs.getString("PLAZAS_PRIMERA")
          );
        }      
      }
    }

    // --- Apartado 4 ---
    public static void eliminarVuelo(Connection cn, String codVuelo) throws SQLException {
      // Sentencia SQL
      String sql = "DELETE FROM VUELOS WHERE COD_VUELO = ?;";
      try (PreparedStatement ps = cn.prepareStatement(sql)){
        ps.setString(1, codVuelo);
        ps.executeUpdate();
        System.out.println("\nVuelo eliminado correctamente.");
      }
      // Sentencia SQL
      sql = "SELECT * FROM VUELOS;";
      try (PreparedStatement ps = cn.prepareStatement(sql)){
        ResultSet rs = ps.executeQuery();
        // Mostrar resultados
        System.out.println("\nResultados:\n");
        System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s%n",
            "COD_VUELO",
            "HORA_SALIDA",
            "DESTINO",
            "PROCEDENCIA",
            "PLAZAS_FUMADOR",
            "PLAZAS_NO_FUMADOR",
            "PLAZAS_TURISTA",
            "PLAZAS_PRIMERA"
        );
        while(rs.next()) {          
          System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s%n",
              rs.getString("COD_VUELO"),
              rs.getString("HORA_SALIDA"),
              rs.getString("DESTINO"),
              rs.getString("PROCEDENCIA"),
              rs.getString("PLAZAS_FUMADOR"),
              rs.getString("PLAZAS_NO_FUMADOR"),
              rs.getString("PLAZAS_TURISTA"),
              rs.getString("PLAZAS_PRIMERA")
          );
        }      
      }
    }
    
    
    // --- Apartado 5 ---
    public static void actualizarPlazasFumadores(Connection cn) throws SQLException {
      // Sentencia SQL
      String sql = "UPDATE VUELOS SET PLAZAS_NO_FUMADOR = PLAZAS_NO_FUMADOR + PLAZAS_FUMADOR, PLAZAS_FUMADOR = 0;";
      try (PreparedStatement ps = cn.prepareStatement(sql)) {
        int filas = ps.executeUpdate();
        System.out.println("\nPlazas de fumadores actualizadas correctamente. (" + filas + " filas modificadas)\n");
      }

      // Mostrar resultados
      sql = "SELECT COD_VUELO, HORA_SALIDA, DESTINO, PROCEDENCIA, PLAZAS_FUMADOR, PLAZAS_NO_FUMADOR FROM VUELOS;";
      try (PreparedStatement ps = cn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        System.out.println("Resultados actualizados:\n");
        System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s%n",
            "COD_VUELO",
            "HORA_SALIDA",
            "DESTINO",
            "PROCEDENCIA",
            "PLAZAS_FUMADOR",
            "PLAZAS_NO_FUMADOR"
        );
        while (rs.next()) {
          System.out.printf("%-18s %-18s %-18s %-18s %-18s %-18s%n",
              rs.getString("COD_VUELO"),
              rs.getString("HORA_SALIDA"),
              rs.getString("DESTINO"),
              rs.getString("PROCEDENCIA"),
              rs.getInt("PLAZAS_FUMADOR"),
              rs.getInt("PLAZAS_NO_FUMADOR")
          );
        }
      }
    }

    // --- Apartado 6 ---
    public static void insertarDatosPasajeros(Connection cn) throws SQLException {
      // Sentencia SQL para crear la tabla si no existe
      String sqlCrear = """
        CREATE TABLE IF NOT EXISTS DATOS_PASAJEROS (
          NUM INT(7) PRIMARY KEY,
          DNI VARCHAR(15),
          NOMBRE VARCHAR(30),
          APELLIDOS VARCHAR(50)
        );
      """;
      try (PreparedStatement ps = cn.prepareStatement(sqlCrear)) {
        ps.executeUpdate();
      }

      // Insertar datos de los pasajeros del vuelo IB-SP-4567
      String sqlInsert = """
        INSERT INTO DATOS_PASAJEROS (NUM, DNI, NOMBRE, APELLIDOS) VALUES
        (123, '12345678A', 'Laura', 'García Pérez'),
        (124, '23456789B', 'Carlos', 'López Díaz'),
        (125, '34567890C', 'María', 'Fernández Ruiz')
        ON DUPLICATE KEY UPDATE DNI = VALUES(DNI), NOMBRE = VALUES(NOMBRE), APELLIDOS = VALUES(APELLIDOS);
      """;
      try (PreparedStatement ps = cn.prepareStatement(sqlInsert)) {
        ps.executeUpdate();
        System.out.println("\nDatos personales insertados correctamente.\n");
      }

      // Mostrar resultados
      String sqlSelect = "SELECT * FROM DATOS_PASAJEROS;";
      try (PreparedStatement ps = cn.prepareStatement(sqlSelect)) {
        ResultSet rs = ps.executeQuery();
        System.out.println("Datos personales de los pasajeros del vuelo IB-SP-4567:\n");
        System.out.printf("%-10s %-15s %-20s %-30s%n",
            "NUM",
            "DNI",
            "NOMBRE",
            "APELLIDOS"
        );
        while (rs.next()) {
          System.out.printf("%-10s %-15s %-20s %-30s%n",
              rs.getInt("NUM"),
              rs.getString("DNI"),
              rs.getString("NOMBRE"),
              rs.getString("APELLIDOS")
          );
        }
      }
    }

}
