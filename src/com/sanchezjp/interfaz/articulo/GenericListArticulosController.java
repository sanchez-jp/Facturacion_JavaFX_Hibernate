/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.articulo;

import com.sanchezjp.interfaz.cliente.*;
import com.sanchezjp.Inicio;
import com.sanchezjp.interfaz.IControlPantallas;
import com.sanchezjp.interfaz.model.ArticuloModel;
import com.sanchezjp.modelo.dao.ArticulosDAO;
import com.sanchezjp.modelo.entities.Articulos;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class GenericListArticulosController implements Initializable, IControlPantallas {

    protected Inicio menuVentanas;
    protected ObservableList<ArticuloModel> listaArticulosModel;
    protected ArticulosDAO articulosDAO;

    @FXML
    protected Button btnInfo;
    @FXML
    protected Button btnVolver;
    @FXML
    protected Button btnEliminar;
    @FXML
    protected Button btnRestaurar;
    @FXML
    protected Button btnEditar;
    @FXML
    protected TableView<ArticuloModel> tvArticulos;    
    @FXML
    protected ComboBox<String> cbBusqueda;
    @FXML
    protected TextField tfBusqueda;
    @FXML
    private TableColumn<ArticuloModel, String> tcCodigo;
    @FXML
    private TableColumn<ArticuloModel, String> tcDescripcion;
    @FXML
    private TableColumn<ArticuloModel, String> tcPrecio;
    @FXML
    private TableColumn<ArticuloModel, Integer> tcStock;
    @FXML
    private TableColumn<ArticuloModel, String> tcIva;
    @FXML
    protected TableColumn<ArticuloModel, String> tcDescuento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        articulosDAO = articulosDAO.getDAO();
        btnInfo.setDisable(true);
        if (btnEliminar != null) {
            btnEliminar.setDisable(true);
        }
        if (btnRestaurar != null) {
            btnRestaurar.setDisable(true);
        }

        tvArticulos.setRowFactory(tv -> {
            TableRow<ArticuloModel> row = new TableRow<>();
            row.setOnMousePressed(event -> {
                if (!row.isEmpty()) {
                    btnInfo.setDisable(false);
                    if (btnEliminar != null) {
                        btnEliminar.setDisable(false);
                    }
                    if (btnRestaurar != null) {
                        btnRestaurar.setDisable(false);
                    }
                    if (btnEditar != null) {
                        btnEditar.setDisable(false);
                    }
                }
            });
            return row;
        });
        tvArticulos.setTableMenuButtonVisible(true);
        ObservableList<String> items = observableArrayList("CÓDIGO", "DESCRIPCIÓN");
        cbBusqueda.getItems().addAll(items);
        listar(getArticulos());
    }

    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }

    protected List<Articulos> getArticulos() {
        return articulosDAO.listAll();
    }

    protected List<Articulos> getArticulosBy(String campo, String valor) {
        return articulosDAO.listBy(campo, valor);
    }

    protected void listar(List<Articulos> listaArticulos) {
        listaArticulosModel = FXCollections.observableArrayList();
        if (btnEliminar != null) {
            listaArticulos.forEach((articulo) -> {
                if (articulo.isActivo()) {
                    listaArticulosModel.add(new ArticuloModel(articulo));
                }
            });
        } else {
            listaArticulos.forEach((articulo) -> {
                if (!articulo.isActivo()) {
                    listaArticulosModel.add(new ArticuloModel(articulo));
                }
            });
        }
        populateTable();
    }

    protected void populateTable() {
        tcCodigo.setCellValueFactory(cellData -> cellData.getValue().getCodArticuloProperty());
        tcDescripcion.setCellValueFactory(cellData -> cellData.getValue().getDescripcionProperty());
        tcPrecio.setCellValueFactory(cellData -> cellData.getValue().getStrPrecioProperty());
        tcStock.setCellValueFactory(cellData -> cellData.getValue().getStockProperty().asObject());
        tcIva.setCellValueFactory(cellData -> cellData.getValue().getStrIvaProperty());
        tcDescuento.setCellValueFactory(cellData -> cellData.getValue().getStrDescuentoProperty());
        tvArticulos.setItems(listaArticulosModel);
        tvArticulos.refresh();
    }

    @FXML
    protected void goToInfo(ActionEvent event) {
        ArticuloModel articulo = tvArticulos.getSelectionModel().getSelectedItem();
        String estado;
        if (articulo.getActivoProperty().get() == true) {
            estado = "activo";
        } else {
            estado = "obsoleto";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de artículo");
        alert.setHeaderText(null);
        alert.setContentText(
                "CÓDIGO: " + articulo.getCodArticuloProperty().get() + "\n"
                + "DESCRIPCIÓN: " + articulo.getDescripcionProperty().get() + "\n"
                + "PRECIO: " + articulo.getPrecioProperty().get() + "€\n"
                + "STOCK: " + articulo.getStockProperty().get() + " unidades.\n"
                + "TIPO IVA: " + articulo.getDescuentoProperty().get() + "%" + "\n"
                + "DESCUENTO APLICADO: " + articulo.getDescuentoProperty().get() + "%" + "\n"
                + "\nESTADO: [" + estado + "]");

        alert.showAndWait();
    }

    @FXML
    protected void goBack(ActionEvent event) {
        menuVentanas.cambiaContenido("Portada2.fxml");
    }

    @FXML
    protected void goToBorrar(ActionEvent event) {
        ArticuloModel articulo = tvArticulos.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eliminar artículo");
        alert.setHeaderText("¡ATENCIÓN!");
        alert.setContentText("Se dispone a eliminar un artículo"
                + "\n¿Confirma que desea elimiar los datos del artículo?");
        alert.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Articulos aux = articulo.getArticulo();
            aux.setActivo(false);
            articulosDAO.update(aux);
            listar(getArticulosBy("codArticulo", "")); // PROBAR // NO BORRA EN BUSQUEDAS

            Alert alertInf = new Alert(AlertType.INFORMATION);
            alertInf.setTitle("Información");
            alertInf.setHeaderText("Artículo eliminado");
            alertInf.setContentText("Ha eliminado el artículo.\n"
                    + "Podrá restaurarlo en el futuro desde la pestaña \"Artículos obsoletos\"");

            alertInf.showAndWait();
        } else {
            alert.close();
        }
    }

    @FXML
    protected void goToRestaurar(ActionEvent event) {
        ArticuloModel articulo = tvArticulos.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Restaurar artículo");
        alert.setHeaderText("¡ATENCIÓN!");
        alert.setContentText("Se dispone a restaurar un artículo"
                + "\n¿Confirma que desea restaurar los datos del artículo?");
        alert.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Articulos aux = articulo.getArticulo();
            aux.setActivo(true);
            articulosDAO.update(aux);
            listar(getArticulosBy("codArticulo", ""));

            Alert alertInf = new Alert(AlertType.INFORMATION);
            alertInf.setTitle("Información");
            alertInf.setHeaderText("Artículo restaurado");
            alertInf.setContentText("Ha restaurado el artículo.\n"
                    + "Ahora podrá encontrarlo en la pestaña \"Listado de artículos\"");

            alertInf.showAndWait();
        } else {
            alert.close();
        }
        
    }

    @FXML
    protected void goToEditar(ActionEvent event) {
        
        try {
            Stage stageEditArticulo = new Stage();
            stageEditArticulo.setTitle("Editor de artículos");
            stageEditArticulo.setResizable(false);
            stageEditArticulo.initStyle(StageStyle.TRANSPARENT);
            stageEditArticulo.setOpacity(1);
            FXMLLoader loader = new FXMLLoader(EdicionArticuloController.class
                    .getResource("EdicionArticulo.fxml"));
            loader.setLocation(EdicionArticuloController.class.
                    getResource("EdicionArticulo.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            stageEditArticulo.initOwner(menuVentanas.getStage());
            // Efecto transicion
            FadeTransition ft = new FadeTransition(Duration.millis(2000), anchorPane);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            Scene escena = new Scene(anchorPane);
            escena.setFill(Color.TRANSPARENT);
            Stage stagePrincipal = menuVentanas.getStage();
            stageEditArticulo.initOwner(stagePrincipal);
            stageEditArticulo.setScene(escena);
            stageEditArticulo.initModality(Modality.WINDOW_MODAL);
            EdicionArticuloController controller = (EdicionArticuloController) loader.getController();
            controller.clienteSeleccionado(tvArticulos.getSelectionModel().getSelectedItem());
            stageEditArticulo.showAndWait();
            
            listar(getArticulos());
        } catch (Exception e) {
            System.err.println("ERROR AL ABRIR NUEVA ESCENA");
        }
    }

    @FXML
    protected void busquedaActiva(KeyEvent event) {
        String filter = cbBusqueda.getValue();
        switch (filter) {
            case "CÓDIGO":
                listar(getArticulosBy("codArticulo", tfBusqueda.getText()));
                break;
            case "DESCRIPCIÓN":
                listar(getArticulosBy("descripcion", tfBusqueda.getText()));
                break;
        }
    }


}
