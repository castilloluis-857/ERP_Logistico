package com.erp.logistica.acceso;

import com.erp.logistica.modelo.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class ClienteDAO {

    /**
     * Lee todos los clientes de la base de datos.
     */
    public ObservableList<Cliente> obtenerTodos() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre_fiscal"),
                        rs.getString("cif_nif"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * NUEVO MÉTODO: Guarda un cliente en la base de datos.
     * @return true si se guardó con éxito.
     */
    public boolean guardarCliente(String nombre, String cif, String email) {
        String sql = "INSERT INTO clientes (nombre_fiscal, cif_nif, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, cif);
            pstmt.setString(3, email);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            return false;
        }
    }
}