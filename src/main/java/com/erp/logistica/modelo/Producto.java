package com.erp.logistica.modelo;

import javafx.beans.property.*;

public class Producto {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();

    // Getters y Setters al estilo JavaFX
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public StringProperty nombreProperty() { return nombre; }
    public void setNombre(String value) { nombre.set(value); }

    public int getStock() { return stock.get(); }
    public IntegerProperty stockProperty() { return stock; }
    public void setStock(int value) { stock.set(value); }

    public double getPrecio() { return precio.get(); }
    public DoubleProperty precioProperty() { return precio; }
    public void setPrecio(double value) { precio.set(value); }
}
