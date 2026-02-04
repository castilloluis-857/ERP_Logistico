package com.erp.logistica.modelo;

import javafx.beans.property.*;

public class Cliente {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombreFiscal = new SimpleStringProperty();
    private final StringProperty cifNif = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Cliente(int id, String nombre, String cif, String email) {
        this.id.set(id);
        this.nombreFiscal.set(nombre);
        this.cifNif.set(cif);
        this.email.set(email);
    }

    public Cliente(String nombre, String cif, String email) {

        this.nombreFiscal.set(nombre);
        this.cifNif.set(cif);
        this.email.set(email);
    }

    // Getters de Propiedad (Para la tabla)
    public IntegerProperty idProperty() { return id; }
    public StringProperty nombreFiscalProperty() { return nombreFiscal; }
    public StringProperty cifNifProperty() { return cifNif; }
    public StringProperty emailProperty() { return email; }

    // Getters normales
    public int getId() { return id.get(); }
    public String getNombreFiscal() { return nombreFiscal.get(); }

    public String getCifNif() {
        return cifNif.get();
    }

    public String getEmail() {
        return email.get();
    }
}