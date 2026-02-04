package com.erp.logistica.principal;

import com.erp.logistica.acceso.*;
import com.erp.logistica.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class FacturaController {
    @FXML private ComboBox<Cliente> cbClientes;
    @FXML private ComboBox<Producto> cbProductos;
    @FXML private ListView<String> lvCarrito;
    @FXML private Label lblTotal;
    @FXML private Spinner<Integer> spCantidad;

    private ObservableList<Producto> listaCarrito = FXCollections.observableArrayList();
    private double totalAcumulado = 0;

    @FXML
    public void initialize() {
        // Cargar combos al iniciar
        refrescarCombos();

        // Configurar los selectores para que muestren nombres legibles
        configurarVisualizacionCombos();
    }

    private void refrescarCombos() {
        cbClientes.setItems(new ClienteDAO().obtenerTodos());
        cbProductos.setItems(new ProductoDAO().obtenerTodos());
    }

    @FXML
    private void añadirAlCarrito() {
        Producto producto = cbProductos.getValue();
        int cant = spCantidad.getValue();

        if (producto != null) {
            listaCarrito.add(producto);
            lvCarrito.getItems().add(producto.getNombre() + " [x" + cant + "] - " + (producto.getPrecio() * cant) + "€");

            totalAcumulado += producto.getPrecio() * cant;
            lblTotal.setText(String.format("%.2f €", totalAcumulado));
        }
    }

    @FXML
    private void finalizarFactura() {
        Cliente cliente = cbClientes.getValue();

        if (cliente == null || listaCarrito.isEmpty()) {
            mostrarAlerta("Error", "Debes seleccionar un cliente y tener productos en el carrito.");
            return;
        }

        // Llamamos al DAO para que haga la magia
        new FacturaDAO().generarFactura(cliente.getId(), listaCarrito, totalAcumulado);

        // --- EL TOQUE DEL 10 ---
        limpiarPantalla();
        refrescarCombos(); // Recargamos productos para ver el stock actualizado
        mostrarAlerta("Éxito", "Factura generada y stock descontado correctamente.");
    }

    private void limpiarPantalla() {
        listaCarrito.clear();
        lvCarrito.getItems().clear();
        totalAcumulado = 0;
        lblTotal.setText("0.00 €");
        cbClientes.getSelectionModel().clearSelection();
        cbProductos.getSelectionModel().clearSelection();
    }

    private void configurarVisualizacionCombos() {
        cbClientes.setConverter(new StringConverter<Cliente>() {
            @Override public String toString(Cliente c) { return (c == null) ? "" : c.getNombreFiscal(); }
            @Override public Cliente fromString(String s) { return null; }
        });
        cbProductos.setConverter(new StringConverter<Producto>() {
            @Override public String toString(Producto p) {
                return (p == null) ? "" : p.getNombre(); }
            @Override public Producto fromString(String s) { return null; }
        });
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}