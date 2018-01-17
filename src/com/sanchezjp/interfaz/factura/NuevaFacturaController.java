/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.factura;

import com.sanchezjp.Inicio;
import com.sanchezjp.interfaz.IControlPantallas;
import com.sanchezjp.modelo.dao.ClientesDAO;
import com.sanchezjp.modelo.entities.Clientes;
import com.sanchezjp.modelo.entities.DetallesFactura;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

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
    @FXML
    private TableColumn<DetallesFactura, String> tcCodArt;
    @FXML
    private TableColumn<DetallesFactura, String> tcDescripción;
    @FXML
    private TableColumn<DetallesFactura, Integer> tcUnidades;
    @FXML
    private TableColumn<DetallesFactura, Double> tcPrecioUnitario;
    @FXML
    private TableColumn<DetallesFactura, Integer> tcDescuento;
    @FXML
    private TableColumn<DetallesFactura, Integer> tcIva;
    @FXML
    private TableColumn<DetallesFactura, Double> tcImporte;
    @FXML
    private TextField tfBaseImp;
    @FXML
    private TextField tfIva;
    @FXML
    private TextField tfTotal;
    @FXML
    private Button btnNuevaLinea;
    
    private final Set<String> possibleNifSet = new HashSet<>();
    private AutoCompletionBinding<String> autoCompletionBinding;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaPossibleNifSet();
        autoCompletionBinding = TextFields.bindAutoCompletion(tfCliente, possibleNifSet);
        autoCompletionBinding.setMaxWidth(85);
        tfCliente.setOnKeyPressed((KeyEvent event) ->{
            switch(event.getCode()){
                case ENTER:learnWord(tfCliente.getText());
                    break;
                default:
                    break;
            }
        });
    }  
    
    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }
    
    /**
     * Registra el valor seleccionado en las sugerencias dentro del text field
     * 
     * @param text texto de sugerencia seleccionado
     */
    private void learnWord(String text){
        possibleNifSet.add(text);
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(tfCliente, possibleNifSet);
    }
    
    /**
     * Carga el conjunto de NIF de clientes existentes en la tabla de 
     * clientes
     */
    private void cargaPossibleNifSet(){
        List<Clientes> clientes = ClientesDAO.getDAO().listAll();
        clientes.stream().forEach((cliente) -> {
            possibleNifSet.add(cliente.getNif());
        });
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
    private void guardarFactura(ActionEvent event) {
    }

    @FXML
    private void addRow(ActionEvent event) {
    }
    
}
