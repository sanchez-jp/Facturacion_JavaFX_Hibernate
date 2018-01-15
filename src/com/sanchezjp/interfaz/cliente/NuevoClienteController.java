/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.cliente;

import com.sanchezjp.Inicio;
import com.sanchezjp.interfaz.IControlPantallas;
import com.sanchezjp.modelo.dao.ClientesDAO;
import com.sanchezjp.modelo.entities.Clientes;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class NuevoClienteController implements Initializable, IControlPantallas {

    private Inicio menuVentanas;
    private ClientesDAO clientesDAO;
    private final char[] letrasDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
        'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    @FXML
    private VBox vbTextFields;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAp1;
    @FXML
    private TextField tfAp2;
    @FXML
    private TextField tfNif;
    @FXML
    private TextField tfRSocial;
    @FXML
    private Label lbError;

    private ComboBox<String> comboDescuento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientesDAO = ClientesDAO.getDAO();
        ArrayList<String> descuentos = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            descuentos.add(i + "%");
        }
        ObservableList<String> items = observableArrayList();
        items.addAll(descuentos);
        comboDescuento = new ComboBox<>(items);
        comboDescuento.getSelectionModel().select(21);
        comboDescuento.setStyle("-fx-border-color: derive(#D35400,50%); " +
                                "-fx-border-radius: 8 8 8 8; " +
                                "-fx-background-radius: 8 8 8 8;");
        vbTextFields.getChildren().add(comboDescuento);
        limitSize(tfNif);
    }

    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }
    
    /**
     * Impone limitaciones al text field del NIF para que sólo sean recogidos
     * caracteres numéricos y una letra
     * 
     * @param textField text field al que se le impone la restricción
     */
    private void limitSize(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.toUpperCase().matches("\\d{0,8}(\\w)?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    /**
     * Comprueba que el valor introducido en el campo tfNif es un valor de NIF
     * válido y no existía previamente en la base de datos
     *
     * @return TRUE si el NIF es válido, FALSE en caso contrario
     */
    private boolean verificarNif() {
        String nif = tfNif.getText().trim().toUpperCase();
        Integer numero;
        Boolean correcto = false;
        if (tfNombre.getText().equals("") || tfAp1.getText().equals("")
                || tfAp2.getText().equals("") || tfNif.getText().equals("")
                || tfRSocial.getText().equals("")) {
            lbError.setText(" Todos los campos de cliente\n"
                    + " excepto el campo \"Descuento\".\n"
                    + " son obligatorios");
            lbError.setVisible(true);
        } else if (nif.length() != 9) {
            lbError.setText(" El NIF debe tener 9 caracteres.\n"
                    + " (8 números y 1 letra).\n"
                    + " No uses espacios ni guiones");
            lbError.setVisible(true);
        } else {
            numero = Integer.parseInt(nif.substring(0, 8));
            if (letrasDNI[numero % 23] != nif.charAt(8)) {
                lbError.setText(" El NIF no existe.\n"
                        + " Comprueba que lo has introducido\n"
                        + " correctamente");
                lbError.setVisible(true);
            } else {
                Clientes cliente = new Clientes();
                cliente.setNif(nif);
                if (clientesDAO.exist(cliente)) {
                    lbError.setText(" El cliente ya existe dentro\n"
                            + " de la base de datos");
                    lbError.setVisible(true);
                } else {
                    lbError.setVisible(false);
                    correcto = true;
                }
            }
        }
        return correcto;
    }

    @FXML
    private void goBack(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setHeaderText("Cambios no guardados");
        alert.setContentText("¿Desea cancelar los cambios realizados?"
                + "\n Se perderán los datos del cliente");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            menuVentanas.cambiaContenido("Portada2.fxml");
        } else {
            alert.close();
        }
    }

    @FXML
    private void guardarCliente(ActionEvent event) {
        if (verificarNif() == true) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Nuevo cliente");
            alert.setContentText("Se va a añadir un nuevo cliente");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Clientes cliente = new Clientes();
                cliente.setNombre(tfNombre.getText());
                cliente.setApellido1(tfAp1.getText());
                cliente.setApellido2(tfAp2.getText());
                cliente.setNif(tfNif.getText());
                cliente.setRazonSocial(tfRSocial.getText());
                cliente.setDescuento(Integer.parseInt(comboDescuento.getValue()
                        .substring(0, comboDescuento.getValue().indexOf("%"))));
                cliente.setActivo(true);
                clientesDAO.add(cliente);

                Alert alertInf = new Alert(AlertType.INFORMATION);
                alertInf.setTitle("Información");
                alertInf.setHeaderText("Cliente añadido");
                alertInf.setContentText("Se ha añadido correctamente un nuevo"
                        + " cliente a la base de datos");

                alertInf.showAndWait();

                menuVentanas.cambiaContenido("Portada2.fxml");
            } else {
                alert.close();
            }
            menuVentanas.cambiaContenido("Portada2.fxml");
        }
    }

}
