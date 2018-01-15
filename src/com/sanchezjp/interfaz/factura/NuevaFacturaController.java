/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.factura;

import com.sanchezjp.Inicio;
import com.sanchezjp.interfaz.IControlPantallas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class NuevaFacturaController implements Initializable , IControlPantallas {

    private Inicio menuVentanas;

    @FXML
    private TextField tfCliente;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNFactura;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfPlanta;
    @FXML
    private TextField tfLetra;
    @FXML
    private TextField tfCPostal;
    @FXML
    private TextField tfLocalidad;
    @FXML
    private TextField tfProvincia;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

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

    @FXML
    private void goToListaClientes(ActionEvent event) {
    }

    @FXML
    private void goToNuevoCliente(ActionEvent event) {
    }

    @FXML
    private void goBack(ActionEvent event) {
    }

    @FXML
    private void guardarCliente(ActionEvent event) {
    }
    
}
