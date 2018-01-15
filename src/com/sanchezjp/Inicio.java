/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.sanchezjp.interfaz.IControlPantallas;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class Inicio extends Application {
    
    private Scene scene;
    private Stage stage;
    
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz/Main.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        IControlPantallas controlador = (IControlPantallas) loader.getController();
        controlador.setMainApp(this);
        cambiaContenido("Portada.fxml");
        stage.setTitle("Administrador de Facturas");
        stage.show();
    }
    
    public boolean cambiaContenido(String archivoFxml) {
        boolean correcto = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz/" + archivoFxml));
            AnchorPane pantalla = (AnchorPane) loader.load();
            BorderPane bp = (BorderPane) scene.getRoot();
            bp.setCenter(pantalla);
            IControlPantallas controlador = (IControlPantallas) loader.getController();
            controlador.setMainApp(this);

        } catch (IOException ex) {
            correcto = false;
            System.err.println("ERROR AL CAMBIAR CONTENIDO CENTRAL");
        }
        return correcto;
    }
    
    public Stage getStage() { // para utilizar dialogos modales 
        return stage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
