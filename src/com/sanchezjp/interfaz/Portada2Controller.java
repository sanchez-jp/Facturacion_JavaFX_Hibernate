/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz;

import com.sanchezjp.Inicio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class Portada2Controller implements Initializable , IControlPantallas{

    private Inicio menuVentanas;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbHeader;
    @FXML
    private Label lbFooter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }
    
}
