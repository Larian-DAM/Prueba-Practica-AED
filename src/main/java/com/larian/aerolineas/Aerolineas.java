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
    
    // --- Apartado 6 ---  
    

}    