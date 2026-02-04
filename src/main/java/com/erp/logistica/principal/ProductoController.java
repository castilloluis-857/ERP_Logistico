package com.erp.logistica.principal;

import com.erp.logistica.acceso.ProductoDAO;
import com.erp.logistica.modelo.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductoController {

    // Vinculación con los elementos del archivo FXML
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Integer> colStock;
    @FXML private TableColumn<Producto, Double> colPrecio;

    @FXML private TextField txtNombre, txtStock, txtPrecio;

    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    public void initialize() {
        // Configuramos las columnas para que lean las propiedades del modelo Producto
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());

        // Carga inicial de datos
        cargarDatos();
    }

    /**
     * Este método rellena la tabla con la información más reciente de la BD.
     */
    private void cargarDatos() {
        tablaProductos.setItems(productoDAO.obtenerTodos());
    }

    @FXML
    private void manejarGuardar() {
        try {
            // 1. Validar que no haya campos vacíos
            if (txtNombre.getText().isEmpty() || txtStock.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
                mostrarAlerta("Campos incompletos", "Por favor, rellena todos los datos del producto.");
                return;
            }

            // 2. Intentar convertir textos a números (Si falla, va al catch)
            String nombre = txtNombre.getText();
            int stock = Integer.parseInt(txtStock.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            // 3. Guardar en la base de datos
            boolean exito = productoDAO.guardarProducto(nombre, stock, precio);

            if (exito) {
                cargarDatos();   // ¡CLAVE! Actualiza la tabla sin reiniciar
                limpiarCampos(); // Deja los cuadros de texto vacíos
            } else {
                mostrarAlerta("Error", "No se pudo guardar el producto en la base de datos.");
            }

        } catch (NumberFormatException e) {
            // Si el usuario escribe letras donde van números, el programa no se cierra
            mostrarAlerta("Formato incorrecto", "Stock y Precio deben ser valores numéricos.");
        }
    }

    @FXML
    private void manejarEliminar() {
        // Miramos qué fila está seleccionada
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            productoDAO.eliminarProducto(seleccionado.getId());
            cargarDatos(); // ¡CLAVE! Actualiza la tabla tras borrar
        } else {
            mostrarAlerta("Atención", "Selecciona un producto de la tabla para eliminarlo.");
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtStock.clear();
        txtPrecio.clear();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}