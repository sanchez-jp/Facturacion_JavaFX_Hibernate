/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.cliente;

import com.sanchezjp.interfaz.model.ClienteModel;
import com.sanchezjp.modelo.dao.ClientesDAO;
import com.sanchezjp.modelo.entities.Clientes;
import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class EdicionClienteController implements Initializable {

    private ClientesDAO clientesDAO;
    private ClienteModel cliente;
    private final char[] letrasDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
        'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    @FXML
    private Button btnCancelar;
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
    
    private ComboBox<String> cbDescuento;

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
        cbDescuento = new ComboBox<>(items);
        vbTextFields.getChildren().add(cbDescuento);
        limitSize(tfNif);
    }
    
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

    @FXML
    private void goToCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToGuardar(ActionEvent event) throws IOException {
        if (verificarNif() == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Actualización de cliente");
            alert.setContentText("Se va a actualizar el cliente");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Clientes cliente = new Clientes();
                cliente.setId(this.cliente.getIdProperty().getValue());
                cliente.setNombre(tfNombre.getText());
                cliente.setApellido1(tfAp1.getText());
                cliente.setApellido2(tfAp2.getText());
                cliente.setNif(tfNif.getText());
                cliente.setRazonSocial(tfRSocial.getText());
                cliente.setDescuento(Integer.parseInt(cbDescuento.getValue()
                        .substring(0, cbDescuento.getValue().indexOf("%"))));
                cliente.setActivo(true);
                this.cliente = new ClienteModel(cliente);
                clientesDAO.update(cliente);

                Alert alertInf = new Alert(Alert.AlertType.INFORMATION);
                alertInf.setTitle("Información");
                alertInf.setHeaderText("Cliente actualizado");
                alertInf.setContentText("Se han actualizado correctamente los"
                        + " datos del cliente");

                alertInf.showAndWait();

                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }
    }

    public void clienteSeleccionado(ClienteModel cliente) {
        this.cliente = cliente;
        tfNombre.setText(cliente.getNombreProperty().getValue());
        tfAp1.setText(cliente.getApellido1Property().getValue());
        tfAp2.setText(cliente.getApellido2Property().getValue());
        tfRSocial.setText(cliente.getRazonSocialProperty().getValue());
        tfNif.setText(cliente.getNifProperty().getValue());
        cbDescuento.getSelectionModel().select(cliente.getDescuentoProperty().getValue());
    }

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
                if (clientesDAO.exist(cliente)
                        && !this.cliente.getNifProperty().getValue().equals(cliente.getNif())) {
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

}
