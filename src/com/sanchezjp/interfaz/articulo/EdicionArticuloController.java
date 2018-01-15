/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.articulo;

import com.sanchezjp.interfaz.model.ArticuloModel;
import com.sanchezjp.modelo.dao.ArticulosDAO;
import com.sanchezjp.modelo.entities.Articulos;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class EdicionArticuloController implements Initializable {

    private ArticulosDAO articulosDAO;
    private ArticuloModel articulo;
    private final String[] ivas = {"0% (exento)", "4% (superreducido)", "10% (reducido)", "21% (general)"};

    @FXML
    private Button btnCancelar;
    @FXML
    private VBox vbTextFields;
    @FXML
    private Label lbError;    
    @FXML
    private AnchorPane panelEdicion;
    @FXML
    private Button btnGuardar;
    @FXML
    private VBox vbLabels;
    @FXML
    private Label lbCodigo;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbPrecio;
    @FXML
    private Label lbStock;
    @FXML
    private Label lbIva;
    @FXML
    private Label lbDescuento;
    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfPrecio;
    @FXML
    private TextField tfStock;
    
    private ComboBox<String> comboDescuento;
    private ComboBox<String> comboIva;
    @FXML
    private TextArea taDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        articulosDAO = articulosDAO.getDAO();
        
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
        
        vbTextFields.getChildren().add(comboIva);
        vbTextFields.getChildren().add(comboDescuento);
        
        restrictToFloatNumber(tfPrecio);
        restrictToIntNumber(tfStock);
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

        if (articulosDAO.exist(articulo) && 
                !codigoArticulo.equals(this.articulo.getCodArticuloProperty().getValue())) {            
            lbError.setText(" El código del artículo \n"
                    + " ya existe");
            lbError.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), lbError);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            //lbError.setVisible(true);
        } else {
            FadeTransition ft = new FadeTransition(Duration.millis(1000), lbError);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.play();
            //lbError.setVisible(false);
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
    private void goToCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToGuardar(ActionEvent event) throws IOException {
        if (verificarCodigo() == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Actualización de artículo");
            alert.setContentText("Se va a actualizar el artículo");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Articulos articulo = new Articulos();
                articulo.setId(this.articulo.getIdProperty().getValue());
                articulo.setCodArticulo(tfCodigo.getText());
                articulo.setDescripcion(taDescripcion.getText());
                articulo.setPrecio(Double.parseDouble(tfPrecio.getText()));
                articulo.setStock(Integer.parseInt(tfStock.getText()));
                articulo.setIva(Integer.parseInt(comboIva.getValue()
                        .substring(0, comboIva.getValue().indexOf("%"))));
                articulo.setDescuento(Integer.parseInt(comboDescuento.getValue()
                        .substring(0, comboDescuento.getValue().indexOf("%"))));
                articulo.setActivo(true);
                this.articulo = new ArticuloModel(articulo);
                articulosDAO.update(articulo);

                Alert alertInf = new Alert(Alert.AlertType.INFORMATION);
                alertInf.setTitle("Información");
                alertInf.setHeaderText("Artículo actualizado");
                alertInf.setContentText("Se han actualizado correctamente los"
                        + " datos del artículo");

                alertInf.showAndWait();

                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                alert.close();
            }
        }
    }

    public void clienteSeleccionado(ArticuloModel articulo) {
        this.articulo = articulo;
        tfCodigo.setText(articulo.getCodArticuloProperty().getValue());
        taDescripcion.setText(articulo.getDescripcionProperty().getValue());
        tfPrecio.setText(String.valueOf(articulo.getPrecioProperty().getValue()));
        tfStock.setText(String.valueOf(articulo.getStockProperty().getValue()));
        comboIva.getSelectionModel().select(articulo.getStrIvaProperty().getValue());
        comboDescuento.getSelectionModel().select(articulo.getStrDescuentoProperty().getValue());
    }

}
