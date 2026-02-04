package com.erp.logistica.principal;

import com.erp.logistica.acceso.ClienteDAO;
import com.erp.logistica.modelo.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {

    // Componentes vinculados al FXML (clientes-view.fxml)
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre, colCif, colEmail;
    @FXML private TextField txtNombre, txtCif, txtEmail;

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        // Configuramos las columnas para que coincidan con los atributos de la clase Cliente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreFiscal"));
        colCif.setCellValueFactory(new PropertyValueFactory<>("cifNif"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Carga inicial
        cargarDatos();
    }

    /**
     * Refresca la tabla leyendo de nuevo la base de datos.
     */
    private void cargarDatos() {
        tablaClientes.setItems(clienteDAO.obtenerTodos());
    }

    /**
     * Acción del botón "Guardar Cliente".
     */
    @FXML
    private void manejarGuardar() {
        String nombre = txtNombre.getText();
        String cif = txtCif.getText();
        String email = txtEmail.getText();

        // 1. Validación simple: No dejar campos vacíos
        if (nombre.isEmpty() || cif.isEmpty() || email.isEmpty()) {
            mostrarAlerta("Atención", "Todos los campos son obligatorios.");
            return;
        }

        // 2. Intentar guardar en la BD
        boolean exito = clienteDAO.guardarCliente(nombre, cif, email);

        if (exito) {
            // ¡ESTO ES EL 10! Refrescamos y limpiamos sin reiniciar la app
            cargarDatos();
            limpiarCampos();
            System.out.println("Cliente guardado con éxito: " + nombre);
        } else {
            mostrarAlerta("Error", "No se pudo guardar el cliente en la base de datos.");
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCif.clear();
        txtEmail.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}