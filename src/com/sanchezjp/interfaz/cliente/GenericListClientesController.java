/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.cliente;

import com.sanchezjp.Inicio;
import com.sanchezjp.interfaz.IControlPantallas;
import com.sanchezjp.interfaz.model.ClienteModel;
import com.sanchezjp.modelo.dao.ClientesDAO;
import com.sanchezjp.modelo.entities.Clientes;
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
import static javafx.collections.FXCollections.observableArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class GenericListClientesController implements Initializable, IControlPantallas {

    protected Inicio menuVentanas;
    protected ObservableList<ClienteModel> listaClientesModel;
    protected ClientesDAO clientesDAO;

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
    protected TableView<ClienteModel> tvClientes;
    @FXML
    protected TableColumn<ClienteModel, String> tcNif;
    @FXML
    protected TableColumn<ClienteModel, String> tcAp1;
    @FXML
    protected TableColumn<ClienteModel, String> tcAp2;
    @FXML
    protected TableColumn<ClienteModel, String> tcNombre;
    @FXML
    protected TableColumn<ClienteModel, String> tcRSocial;
    @FXML
    protected TableColumn<ClienteModel, String> tcDescuento;
    @FXML
    protected ComboBox<String> cbBusqueda;
    @FXML
    protected TextField tfBusqueda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        clientesDAO = ClientesDAO.getDAO();
        btnInfo.setDisable(true);
        if (btnEliminar != null) {
            btnEliminar.setDisable(true);
        }
        if (btnRestaurar != null) {
            btnRestaurar.setDisable(true);
        }

        tvClientes.setRowFactory(tv -> {
            TableRow<ClienteModel> row = new TableRow<>();
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
        tvClientes.setTableMenuButtonVisible(true);
        ObservableList<String> items = observableArrayList("NIF", "NOMBRE", "APELLIDO 1", "APELLIDO 2", "RAZÓN SOCIAL");
        cbBusqueda.getItems().addAll(items);
        listar(getClientes());
    }

    @Override
    public void setMainApp(Inicio mainApp) {
        menuVentanas = mainApp;
    }

    protected List<Clientes> getClientes() {
        return clientesDAO.listAll();
    }

    protected List<Clientes> getClientesBy(String campo, String valor) {
        return clientesDAO.listBy(campo, valor);
    }

    protected void listar(List<Clientes> listaClientes) {
        listaClientesModel = FXCollections.observableArrayList();
        if (btnEliminar != null) {
            listaClientes.forEach((cliente) -> {
                if (cliente.isActivo()) {
                    listaClientesModel.add(new ClienteModel(cliente));
                }
            });
        } else {
            listaClientes.forEach((cliente) -> {
                if (!cliente.isActivo()) {
                    listaClientesModel.add(new ClienteModel(cliente));
                }
            });
        }
        populateTable();
    }

    protected void populateTable() {
        tcNif.setCellValueFactory(cellData -> cellData.getValue().getNifProperty());
        tcAp1.setCellValueFactory(cellData -> cellData.getValue().getApellido1Property());
        tcAp2.setCellValueFactory(cellData -> cellData.getValue().getApellido2Property());
        tcNombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        tcRSocial.setCellValueFactory(cellData -> cellData.getValue().getRazonSocialProperty());
        tcDescuento.setCellValueFactory(cellData -> cellData.getValue().getStrDescuentoProperty());
        tvClientes.setItems(listaClientesModel);
        tvClientes.refresh();
    }

    @FXML
    protected void goToInfo(ActionEvent event) {
        ClienteModel cliente = tvClientes.getSelectionModel().getSelectedItem();
        String estado;
        if (cliente.getActivoProperty().get() == true) {
            estado = "activo";
        } else {
            estado = "obsoleto";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de cliente");
        alert.setHeaderText(null);
        alert.setContentText(
                "NIF: " + cliente.getNifProperty().get() + "\n"
                + "NOMBRE: " + cliente.getNombreProperty().get() + "\n"
                + "APELLIDOS: " + cliente.getApellido1Property().get() + " " + cliente.getApellido2Property().get() + "\n"
                + "RAZÓN SOCIAL: " + cliente.getRazonSocialProperty().get() + "\n"
                + "DESCUENTO APLICADO: " + cliente.getDescuentoProperty().get() + "%" + "\n"
                + "\nESTADO: [" + estado + "]");

        alert.showAndWait();
    }

    @FXML
    protected void goBack(ActionEvent event) {
        menuVentanas.cambiaContenido("Portada2.fxml");
    }

    @FXML
    protected void goToBorrar(ActionEvent event) {
        ClienteModel cliente = tvClientes.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Eliminar cliente");
        alert.setHeaderText("¡ATENCIÓN!");
        alert.setContentText("Se dispone a eliminar un cliente"
                + "\n¿Confirma que desea elimiar los datos del cliente?");
        alert.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Clientes aux = cliente.getCliente();
            aux.setActivo(false);
            clientesDAO.update(aux);
            listar(getClientesBy("nif", "")); // PROBAR // NO BORRA EN BUSQUEDAS

            Alert alertInf = new Alert(AlertType.INFORMATION);
            alertInf.setTitle("Información");
            alertInf.setHeaderText("Cliente eliminado");
            alertInf.setContentText("Ha eliminado el cliente.\n"
                    + "Podrá restaurarlo en el futuro desde la pestaña \"Clientes obsoletos\"");

            alertInf.showAndWait();
        } else {
            alert.close();
        }
    }

    @FXML
    protected void goToRestaurar(ActionEvent event) {
        ClienteModel cliente = tvClientes.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("restaurar cliente");
        alert.setHeaderText("¡ATENCIÓN!");
        alert.setContentText("Se dispone a restaurar un cliente"
                + "\n¿Confirma que desea restaurar los datos del cliente?");
        alert.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Clientes aux = cliente.getCliente();
            aux.setActivo(true);
            clientesDAO.update(aux);
            listar(getClientesBy("nif", ""));

            Alert alertInf = new Alert(AlertType.INFORMATION);
            alertInf.setTitle("Información");
            alertInf.setHeaderText("Cliente restaurado");
            alertInf.setContentText("Ha restaurado el cliente.\n"
                    + "Ahora podrá encontrarlo en la pestaña \"Listado de clientes\"");

            alertInf.showAndWait();
        } else {
            alert.close();
        }
        
    }

    @FXML
    protected void goToEditar(ActionEvent event) {
        
        try {
            Stage stageEditCliente = new Stage();
            stageEditCliente.setTitle("Editor de cliente");
            stageEditCliente.setResizable(false);
            stageEditCliente.initStyle(StageStyle.TRANSPARENT);
            stageEditCliente.setOpacity(1);
            FXMLLoader loader = new FXMLLoader(EdicionClienteController.class
                    .getResource("EdicionCliente.fxml"));
            loader.setLocation(EdicionClienteController.class.
                    getResource("EdicionCliente.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            stageEditCliente.initOwner(menuVentanas.getStage());
            // Efecto transicion
            FadeTransition ft = new FadeTransition(Duration.millis(2000), anchorPane);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            Scene escena = new Scene(anchorPane);
            escena.setFill(Color.TRANSPARENT);
            Stage stagePrincipal = menuVentanas.getStage();
            stageEditCliente.initOwner(stagePrincipal);
            stageEditCliente.setScene(escena);
            stageEditCliente.initModality(Modality.WINDOW_MODAL);
            EdicionClienteController controller = (EdicionClienteController) loader.getController();
            controller.clienteSeleccionado(tvClientes.getSelectionModel().getSelectedItem());
            stageEditCliente.showAndWait();
            
            listar(getClientes());
            
        } catch (Exception e) {
            System.err.println("ERROR AL ABRIR NUEVA ESCENA");
        }
    }

    @FXML
    protected void busquedaActiva(KeyEvent event) {
        String filter = cbBusqueda.getValue();
        switch (filter) {
            case "NIF":
                listar(getClientesBy("nif", tfBusqueda.getText()));
                break;
            case "NOMBRE":
                listar(getClientesBy("nombre", tfBusqueda.getText()));
                break;
            case "APELLIDO 1":
                listar(getClientesBy("apellido1", tfBusqueda.getText()));
                break;
            case "APELLIDO 2":
                listar(getClientesBy("apellido2", tfBusqueda.getText()));
                break;
            case "RAZÓN SOCIAL":
                listar(getClientesBy("razonSocial", tfBusqueda.getText()));
                break;
        }
    }


}
