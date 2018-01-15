/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz;

import com.sanchezjp.Inicio;
import com.sanchezjp.modelo.dao.ArticulosDAO;
import com.sanchezjp.modelo.dao.ClientesDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class MainController implements Initializable, IControlPantallas {

    private Inicio menuVentanas;

    @FXML
    private MenuBar menuBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientesDAO.getDAO().listAll();
        ArticulosDAO.getDAO().listAll();
    }
    
    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }

    @FXML
    private void goToNuevaFactura(ActionEvent event) {
        menuVentanas.cambiaContenido("factura/NuevaFactura.fxml");
    }

    @FXML
    private void goToListFacturas(ActionEvent event) {
    }

    @FXML
    private void goToNewCliente(ActionEvent event) {
        menuVentanas.cambiaContenido("cliente/NuevoCliente.fxml");
    }

    @FXML
    private void goToListCliente(ActionEvent event) {
        menuVentanas.cambiaContenido("cliente/ListadoClientes.fxml");
    }

    @FXML
    private void goToPapeleraClientes(ActionEvent event) {
        menuVentanas.cambiaContenido("cliente/PapeleraClientes.fxml");
    }

    @FXML
    private void goToNewArticulo(ActionEvent event) {
        menuVentanas.cambiaContenido("articulo/NuevoArticulo.fxml");
    }

    @FXML
    private void goToListArticulo(ActionEvent event) {
        menuVentanas.cambiaContenido("articulo/ListadoArticulos.fxml");
    }
    
    @FXML
    private void goToPapeleraArticulos(ActionEvent event) {
        menuVentanas.cambiaContenido("articulo/PapeleraArticulos.fxml");
    }

    @FXML
    private void goToAboutApp(ActionEvent event) {
    }

    @FXML
    private void goToPapeleraFactura(ActionEvent event) {
    }
    
}
