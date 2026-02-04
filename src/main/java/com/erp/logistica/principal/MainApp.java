package com.erp.logistica.principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Cargamos el archivo FXML

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/erp/logistica/hello-view.fxml"));

        // Creamos la escena (la ventana)
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        Image icon = new Image(getClass().getResourceAsStream("/com/erp/logistica/logo-erp.png"));
        stage.getIcons().add(icon);
        stage.setTitle("ERP Logística Zaragoza");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
