package com.erp.logistica.acceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/erp_logistica";
    private static final String USER = "root"; // Tu usuario de MySQL
    private static final String PASSWORD = "root"; // Tu contraseña de MySQL

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Registrar el driver (opcional en versiones nuevas, pero buena práctica)
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos de Zaragoza.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("No se encontró el driver de MySQL", e);
            }
        }
        return connection;
    }
}
