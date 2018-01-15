/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz;

import com.sanchezjp.Inicio;
import com.sanchezjp.modelo.dao.ArticulosDAO;
import com.sanchezjp.modelo.dao.ClientesDAO;
import com.sanchezjp.modelo.dao.FacturasDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class PortadaController implements Initializable, IControlPantallas {

    private Inicio menuVentanas;

    @FXML
    private Label lbInfo;
    @FXML
    private Label lbHeader;
    @FXML
    private Label lbFooter;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ProgressBar progressBar;

    public Timeline timeline;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.millis(4000),
                        new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.playFromStart();
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                lbInfo.setText("Cargando información");
            }
        });

        Task<Void> task = new Task() {
            @Override
            protected Void call() throws Exception {
//                new ArticulosDAO();
//                new ClientesDAO();
//                new FacturasDAO();
                Thread.sleep(4000); // Retardo provisional para ver el efecto
                return null;
            }

            @Override
            protected void failed() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbInfo.setText("Fallo en la conexión con la Base de Datos");
                    }
                });
            }

            @Override
            protected void succeeded() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbInfo.setText("Listo");
                        progressBar.getStylesheets().add(getClass()
                                .getResource("customizations.css").toExternalForm());
                        progressBar.getStyleClass().add("green-bar");
                        progressBar.setDisable(true);
                    }
                });
            }
        };
        new Thread(task).start();
    }

    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }

}
