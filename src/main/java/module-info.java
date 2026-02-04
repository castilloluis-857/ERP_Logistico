module com.erp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    // Estas son opcionales según si las usas, pero déjalas si ya estaban
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    // 1. Permite que JavaFX acceda a tus controladores (donde estará el @FXML)
    opens com.erp.logistica.principal to javafx.fxml;

    // 2. MUY IMPORTANTE: Permite que JavaFX lea los atributos de tus clases (Producto, Cliente)
    // para poder mostrarlos en las tablas (TableView)
    opens com.erp.logistica.modelo to javafx.base;

    exports com.erp.logistica.principal;
}