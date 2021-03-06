package com.sanchezjp.modelo.entities;
// Generated 27-dic-2017 22:08:04 by Hibernate Tools 4.3.1


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Facturas generated by hbm2java
 */
public class Facturas  implements java.io.Serializable {


     private Integer id;
     private Clientes clientes;
     private Date fechaFactura;
     private int numFactura;
     private String calle;
     private int numero;
     private Integer planta;
     private String letra;
     private String codPostal;
     private String localidad;
     private String provincia;
     private String obsoleto;
     private Set detallesFacturas = new HashSet(0);

    public Facturas() {
    }

	
    public Facturas(Clientes clientes, Date fechaFactura, int numFactura, String calle, int numero, String codPostal, String localidad, String provincia, String obsoleto) {
        this.clientes = clientes;
        this.fechaFactura = fechaFactura;
        this.numFactura = numFactura;
        this.calle = calle;
        this.numero = numero;
        this.codPostal = codPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.obsoleto = obsoleto;
    }
    public Facturas(Clientes clientes, Date fechaFactura, int numFactura, String calle, int numero, Integer planta, String letra, String codPostal, String localidad, String provincia, String obsoleto, Set detallesFacturas) {
       this.clientes = clientes;
       this.fechaFactura = fechaFactura;
       this.numFactura = numFactura;
       this.calle = calle;
       this.numero = numero;
       this.planta = planta;
       this.letra = letra;
       this.codPostal = codPostal;
       this.localidad = localidad;
       this.provincia = provincia;
       this.obsoleto = obsoleto;
       this.detallesFacturas = detallesFacturas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Clientes getClientes() {
        return this.clientes;
    }
    
    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }
    public Date getFechaFactura() {
        return this.fechaFactura;
    }
    
    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
    public int getNumFactura() {
        return this.numFactura;
    }
    
    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }
    public String getCalle() {
        return this.calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public int getNumero() {
        return this.numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public Integer getPlanta() {
        return this.planta;
    }
    
    public void setPlanta(Integer planta) {
        this.planta = planta;
    }
    public String getLetra() {
        return this.letra;
    }
    
    public void setLetra(String letra) {
        this.letra = letra;
    }
    public String getCodPostal() {
        return this.codPostal;
    }
    
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
    public String getLocalidad() {
        return this.localidad;
    }
    
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    public String getObsoleto() {
        return this.obsoleto;
    }
    
    public void setObsoleto(String obsoleto) {
        this.obsoleto = obsoleto;
    }
    public Set getDetallesFacturas() {
        return this.detallesFacturas;
    }
    
    public void setDetallesFacturas(Set detallesFacturas) {
        this.detallesFacturas = detallesFacturas;
    }

    public Integer getAnioFactura() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return Integer.parseInt(df.format(fechaFactura));
    }


}


