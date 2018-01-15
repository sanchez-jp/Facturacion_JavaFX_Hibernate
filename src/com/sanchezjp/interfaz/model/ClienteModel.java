/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.model;

import com.sanchezjp.modelo.entities.Clientes;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class ClienteModel {

    private Clientes cliente;
    private final SimpleIntegerProperty idProperty;
    private final SimpleStringProperty nifProperty;
    private final SimpleStringProperty nombreProperty;
    private final SimpleStringProperty apellido1Property;
    private final SimpleStringProperty apellido2Property;
    private final SimpleStringProperty razonSocialProperty;
    private final SimpleObjectProperty<Integer> descuentoProperty;
    private final SimpleObjectProperty<Boolean> activoProperty;
    private final SimpleObjectProperty<String> strDescuentoProperty;
    // SimpleObjectProperty<Integer> en vez de SimpleIntegerProperty para aceptar valores nulos
    
    public ClienteModel() {
        this(null);
    }

    public ClienteModel(Clientes cliente) {
        this.cliente = cliente;

        if (cliente != null) {
            idProperty = new SimpleIntegerProperty(cliente.getId());
            nifProperty = new SimpleStringProperty(cliente.getNif());
            nombreProperty = new SimpleStringProperty(cliente.getNombre());
            apellido1Property = new SimpleStringProperty(cliente.getApellido1());
            apellido2Property = new SimpleStringProperty(cliente.getApellido2());
            razonSocialProperty = new SimpleStringProperty(cliente.getRazonSocial());
            descuentoProperty = new SimpleObjectProperty<>(cliente.getDescuento());
            activoProperty = new SimpleObjectProperty<>(cliente.isActivo());
            strDescuentoProperty = new SimpleObjectProperty<>(cliente.getDescuento()+"%");
        } else {
            idProperty = null;
            nifProperty = null;
            nombreProperty = null;
            apellido1Property = null;
            apellido2Property = null;
            razonSocialProperty = null;
            descuentoProperty = null;
            activoProperty = null;
            strDescuentoProperty = null;
        }
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public SimpleIntegerProperty getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(Integer id) {
        this.idProperty.set(id);
    }
    
    public SimpleStringProperty getNifProperty() {
        return nifProperty;
    }

    public void setNifProperty(String nif) {
        this.nifProperty.set(nif);
    }

    public SimpleStringProperty getNombreProperty() {
        return nombreProperty;
    }

    public void setNombreProperty(String nombre) {
        this.nombreProperty.set(nombre);
    }

    public SimpleStringProperty getApellido1Property() {
        return apellido1Property;
    }

    public void setApellido1Property(String apellido1) {
        this.apellido1Property.set(apellido1);
    }

    public SimpleStringProperty getApellido2Property() {
        return apellido2Property;
    }

    public void setApellido2Property(String apellido2) {
        this.apellido2Property.set(apellido2);
    }

    public SimpleStringProperty getRazonSocialProperty() {
        return razonSocialProperty;
    }

    public void setRazonSocialProperty(String razonSocial) {
        this.razonSocialProperty.set(razonSocial);
    }

    public SimpleObjectProperty<Integer> getDescuentoProperty() {
        return descuentoProperty;
    }

    public void setDescuentoProperty(Integer descuento) {
        this.descuentoProperty.set(descuento);
    }

    public SimpleObjectProperty<Boolean> getActivoProperty() {
        return activoProperty;
    }
    
    public void setActivoProperty(Boolean activo){
        this.activoProperty.set(activo);
    }
    
    public SimpleObjectProperty<String> getStrDescuentoProperty() {
        return strDescuentoProperty;
    }
    
    public void setStrDescuentoProperty(String descuento){
        this.strDescuentoProperty.set(descuento);
    }

}
