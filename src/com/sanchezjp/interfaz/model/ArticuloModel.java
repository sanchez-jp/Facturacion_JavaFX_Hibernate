/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanchezjp.interfaz.model;

import com.sanchezjp.modelo.entities.Articulos;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 */
public class ArticuloModel {
    
    private Articulos articulo;
    private final SimpleIntegerProperty idProperty;
    private final SimpleStringProperty codArticuloProperty;
    private final SimpleStringProperty descripcionProperty;
    private final SimpleDoubleProperty precioProperty;
    private final SimpleIntegerProperty stockProperty;
    private final SimpleIntegerProperty ivaProperty;
    private final SimpleObjectProperty<Integer> descuentoProperty;
    private final SimpleObjectProperty<Boolean> activoProperty;
    
    private final SimpleObjectProperty<String> strPrecioProperty;
    private final SimpleObjectProperty<String> strIvaProperty;
    private final SimpleObjectProperty<String> strDescuentoProperty;
    
    public ArticuloModel() {
        this(null);
    }
    
    public ArticuloModel(Articulos articulo) {
        this.articulo = articulo;
        
        if (articulo != null) {
            this.idProperty = new SimpleIntegerProperty(articulo.getId());
            this.codArticuloProperty = new SimpleStringProperty(articulo.getCodArticulo());
            this.descripcionProperty = new SimpleStringProperty(articulo.getDescripcion());
            this.precioProperty = new SimpleDoubleProperty(articulo.getPrecio());
            this.stockProperty = new SimpleIntegerProperty(articulo.getStock());
            this.ivaProperty = new SimpleIntegerProperty(articulo.getIva());
            this.descuentoProperty = new SimpleObjectProperty(articulo.getDescuento());
            this.activoProperty = new SimpleObjectProperty<>(articulo.isActivo());
            
            this.strPrecioProperty = new SimpleObjectProperty<>(articulo.getPrecio() + "€");
            this.strIvaProperty = new SimpleObjectProperty<>(articulo.getIva() + "%");
            this.strDescuentoProperty = new SimpleObjectProperty<>(articulo.getDescuento() + "%");
            
        } else {
            this.idProperty = null;
            this.codArticuloProperty = null;
            this.descripcionProperty = null;
            this.precioProperty = null;
            this.stockProperty = null;
            this.ivaProperty = null;
            this.descuentoProperty = null;
            this.activoProperty = null;
            
            this.strPrecioProperty = null;
            this.strIvaProperty = null;
            this.strDescuentoProperty = null;
        }
    }
    
    public Articulos getArticulo() {
        return articulo;
    }
    
    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }
    
    public SimpleIntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setIdProperty(Integer id) {
        this.idProperty.set(id);
    }
    
    public SimpleStringProperty getCodArticuloProperty() {
        return codArticuloProperty;
    }
    
    public void setCodArticuloProperty(String codArticulo) {
        this.codArticuloProperty.set(codArticulo);
    }
    
    public SimpleStringProperty getDescripcionProperty() {
        return descripcionProperty;
    }
    
    public void setDescripcionProperty(String descripcion) {
        this.descripcionProperty.set(descripcion);
    }
    
    public SimpleDoubleProperty getPrecioProperty() {
        return precioProperty;
    }
    
    public void setPrecioProperty(Double precio) {
        this.precioProperty.set(precio);
    }
    
    public SimpleIntegerProperty getStockProperty() {
        return stockProperty;
    }
    
    public void setStockProperty(Integer stock) {
        this.stockProperty.set(stock);
    }
    
    public SimpleIntegerProperty getIvaProperty() {
        return ivaProperty;
    }
    
    public void setIvaProperty(Integer iva) {
        this.ivaProperty.set(iva);
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
    
    
    public SimpleObjectProperty<String> getStrPrecioProperty() {
        return strPrecioProperty;
    }
    
    public void setStrPrecioProperty(String precio){
        this.strPrecioProperty.set(precio);
    }
    
    public SimpleObjectProperty<String> getStrIvaProperty() {
        return strIvaProperty;
    }
    
    public void setStrIvaProperty(String iva){
        this.strIvaProperty.set(iva);
    }
    
    public SimpleObjectProperty<String> getStrDescuentoProperty() {
        return strDescuentoProperty;
    }
    
    public void setStrDescuentoProperty(String descuento){
        this.strDescuentoProperty.set(descuento);
    }
    
}
