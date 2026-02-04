package com.erp.logistica.acceso;

import com.erp.logistica.modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class ProductoDAO {

    /**
     * Obtiene todos los productos de la base de datos.
     * Ideal para llenar la tabla al iniciar o refrescar.
     */
    public ObservableList<Producto> obtenerTodos() {
        ObservableList<Producto> lista = FXCollections.observableArrayList();
        String sql = "SELECT id, nombre, stock, precio_venta FROM productos";

        // Uso de Try-with-resources: cierra la conexión automáticamente al terminar
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.idProperty().set(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setStock(rs.getInt("stock"));
                p.setPrecio(rs.getDouble("precio_venta"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al leer productos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda un nuevo producto.
     * Devuelve true si se guardó correctamente.
     */
    public boolean guardarProducto(String nombre, int stock, double precio) {
        String sql = "INSERT INTO productos (nombre, stock, precio_venta) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, precio);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un producto por su ID.
     */
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}