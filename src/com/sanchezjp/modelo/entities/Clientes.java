package com.sanchezjp.modelo.entities;
// Generated 27-dic-2017 22:08:04 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Clientes generated by hbm2java
 */
public class Clientes  implements java.io.Serializable {


     private Integer id;
     private String nif;
     private String nombre;
     private String apellido1;
     private String apellido2;
     private String razonSocial;
     private Integer descuento;
     private String obsoleto;
     private Set facturases = new HashSet(0);

    public Clientes() {
        this(null,null,null,null,null,null,null,null);
    }

	
    public Clientes(String nombre, String apellido1, String obsoleto) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.obsoleto = obsoleto;
    }
    public Clientes(String nif, String nombre, String apellido1, String apellido2, String razonSocial, Integer descuento, String obsoleto, Set facturases) {
       this.nif = nif;
       this.nombre = nombre;
       this.apellido1 = apellido1;
       this.apellido2 = apellido2;
       this.razonSocial = razonSocial;
       this.descuento = descuento;
       this.obsoleto = obsoleto;
       this.facturases = facturases;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNif() {
        return this.nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido1() {
        return this.apellido1;
    }
    
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return this.apellido2;
    }
    
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String getRazonSocial() {
        return this.razonSocial;
    }
    
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public Integer getDescuento() {
        return this.descuento;
    }
    
    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }
    public String getObsoleto() {
        return this.obsoleto;
    }
    
    private void setObsoleto(String obsoleto) {
        this.obsoleto = obsoleto;
    }

    public Set getFacturases() {
        return this.facturases;
    }
    
    public void setFacturases(Set facturases) {
        this.facturases = facturases;
    }
    
    /**
     * Establece la entidad como activa o inactiva (Borrado Lógico)
     * @param activo indica si está obsoleto (false) o no (true)
     */
    public void setActivo(Boolean activo){
        if(activo){
            setObsoleto(OBSOLETO.NO.toString());
        }else{
            setObsoleto(OBSOLETO.YES.toString());
        }
    }
    
    /**
     * Indica si se ha practicado un borrado lógico sobre el elemento
     * @return TRUE si no hay borrado lógico (no obsoleto), FALSE en caso contrario.
     */
    public Boolean isActivo(){
        return obsoleto.equals(OBSOLETO.NO.toString());
    }
    
}


