/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.articulo;

import com.sanchezjp.Inicio;
import com.sanchezjp.interfaz.IControlPantallas;
import com.sanchezjp.modelo.dao.ArticulosDAO;
import com.sanchezjp.modelo.entities.Articulos;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
import static javafx.collections.FXCollections.observableArrayList;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class NuevoArticuloController implements Initializable, IControlPantallas {

    private Inicio menuVentanas;
    private ArticulosDAO articulosDAO;
    private final String[] ivas = {"0% (exento)", "4% (superreducido)", "10% (reducido)", "21% (general)"};
    
    @FXML
    private VBox vbTextFields;
    @FXML
    private Label lbError;
    @FXML
    private TextField tfCodigo;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfPrecio;
    @FXML
    private TextField tfStock;

    private ComboBox<String> comboDescuento;
    private ComboBox<String> comboIva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        articulosDAO = ArticulosDAO.getDAO();

        ObservableList<String> itemsIva = observableArrayList();
        itemsIva.addAll(ivas);
        comboIva = new ComboBox<>(itemsIva);
        comboIva.setStyle("-fx-border-color: derive(#D35400,50%); " +
                          "-fx-border-radius: 8 8 8 8; " +
                          "-fx-background-radius: 8 8 8 8;");

        ArrayList<String> descuentos = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            descuentos.add(i + "%");
        }
        ObservableList<String> itemsDescuento = observableArrayList();
        itemsDescuento.addAll(descuentos);
        comboDescuento = new ComboBox<>(itemsDescuento);
        comboDescuento.setStyle("-fx-border-color: derive(#D35400,50%); " +
                                "-fx-border-radius: 8 8 8 8; " +
                                "-fx-background-radius: 8 8 8 8;");

        comboIva.getSelectionModel().select(3);
        comboDescuento.getSelectionModel().select(0);
        
        vbTextFields.getChildren().add(comboIva);
        vbTextFields.getChildren().add(comboDescuento);

        restrictToFloatNumber(tfPrecio);
        restrictToIntNumber(tfStock);
    }

    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }

    /**
     * Verifica que el código de artículo introducido no exista previamente
     * en la base de datos
     * 
     * @return TRUE si el código no existe, FALSE en caso contrario
     */
    private boolean verificarCodigo() {
        String codigoArticulo = tfCodigo.getText().trim().toUpperCase();
        Articulos articulo = new Articulos();
        articulo.setCodArticulo(codigoArticulo);
        Boolean correcto = false;

        if (articulosDAO.exist(articulo)) {
            lbError.setText(" El código del artículo \n"
                    + " ya existe");
            lbError.setVisible(true);
        } else {
            lbError.setVisible(false);
            correcto = true;
        }
        
        return correcto;
    }

    /**
     * Restringe la entrada en un campo de texto a valores numéricos de coma
     * flotante
     *
     * @param textField text field al que se le aplica la restricción
     */
    private void restrictToFloatNumber(TextField textField) {
        textField.textProperty().addListener((ObservableValue<? extends String> observable,
                String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Restringe la entrada en un campo de texto a valores numéricos enteros
     *
     * @param textField
     */
    private void restrictToIntNumber(TextField textField) {
        textField.textProperty().addListener((ObservableValue<? extends String> observable,
                String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}?")) {
                textField.setText(oldValue);
            }
        });
    }

    @FXML
    private void goBack(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setHeaderText("Cambios no guardados");
        alert.setContentText("¿Desea cancelar los cambios realizados?"
                + "\n Se perderán los datos del artícuo");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            menuVentanas.cambiaContenido("Portada2.fxml");
        } else {
            alert.close();
        }
    }

    @FXML
    private void guardarArticulo(ActionEvent event) {
        if (verificarCodigo() == true) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Nuevo artículo");
            alert.setContentText("Se va a añadir un nuevo artículo");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Articulos articulo = new Articulos();
                articulo.setCodArticulo(tfCodigo.getText());
                articulo.setDescripcion(taDescripcion.getText());
                articulo.setPrecio(Double.parseDouble(tfPrecio.getText()));
                articulo.setStock(Integer.parseInt(tfStock.getText()));
                articulo.setIva(Integer.parseInt(comboIva.getValue().
                        substring(0, comboIva.getValue().indexOf("%"))));
                articulo.setDescuento(Integer.parseInt(comboDescuento.getValue()
                        .substring(0, comboDescuento.getValue().indexOf("%"))));
                articulo.setActivo(true);
                articulosDAO.add(articulo);

                Alert alertInf = new Alert(AlertType.INFORMATION);
                alertInf.setTitle("Información");
                alertInf.setHeaderText("Artículo añadido");
                alertInf.setContentText("Se ha añadido correctamente un nuevo"
                        + " artículo a la base de datos");

                alertInf.showAndWait();

                menuVentanas.cambiaContenido("Portada2.fxml");
            } else {
                alert.close();
            }
            menuVentanas.cambiaContenido("Portada2.fxml");
        }
    }

    /**
     * Devuelve un valor de IVA según el valor del campo de texto
     * 
     * @param value cadena que contiene el valor del campo de texto
     * @return 
     */
    private int valorIva(String value) {
        int iva;
        switch (value) {
            case "0% (exento)":
            default:
                iva = 0;
                break;
            case "4% (superreducido)":
                iva = 4;
                break;
            case "10% (reducido)":
                iva = 10;
                break;
            case "21% (general)":
                iva = 21;
                break;
        }
        return iva;
    }

}
