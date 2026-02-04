package com.erp.logistica.acceso;

import com.erp.logistica.modelo.Producto;
import java.sql.*;
import java.util.List;

public class FacturaDAO {

    /**
     * Registra una factura completa, sus líneas y descuenta el stock.
     * Todo ocurre dentro de una transacción: o se guarda todo o nada.
     */
    public void generarFactura(int clienteId, List<Producto> carrito, double total) {
        String sqlFactura = "INSERT INTO facturas (cliente_id, total) VALUES (?, ?)";
        String sqlLinea = "INSERT INTO lineas_factura (factura_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(true); // 1. PAUSA: No guardes nada hasta que yo te diga "commit"

            // 2. Insertar la cabecera de la factura
            try (PreparedStatement psF = conn.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
                psF.setInt(1, clienteId);
                psF.setDouble(2, total);
                psF.executeUpdate();

                ResultSet rs = psF.getGeneratedKeys();
                if (rs.next()) {
                    int idFactura = rs.getInt(1);

                    // 3. Preparar las líneas y el stock
                    try (PreparedStatement psL = conn.prepareStatement(sqlLinea);
                         PreparedStatement psS = conn.prepareStatement(sqlStock)) {

                        for (Producto p : carrito) {
                            // Línea de detalle
                            psL.setInt(1, idFactura);
                            psL.setInt(2, p.getId());
                            psL.setInt(3, 1); // Cantidad fija por ahora
                            psL.setDouble(4, p.getPrecio());
                            psL.addBatch();

                            // Descontar stock
                            psS.setInt(1, 1);
                            psS.setInt(2, p.getId());
                            psS.addBatch();
                        }
                        psL.executeBatch();
                        psS.executeBatch();
                    }
                }
            }
            conn.commit(); // 4. TODO OK: Guardamos permanentemente
            System.out.println("Venta finalizada con éxito.");

        } catch (SQLException e) {
            // 5. ERROR: Si algo falló (ej: no había stock suficiente o error de red), deshacemos todo
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Error en factura: " + e.getMessage());
        }
    }
}