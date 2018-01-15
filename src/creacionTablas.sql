/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Juan Pedro Sánchez Álvarez (sanchezalvarezjp@gmail.com)
 * Created: 26-dic-2017
 */

/*
DROP TABLE IF EXISTS detalles_factura;
DROP TABLE IF EXISTS facturas;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS articulos;
*/

CREATE TABLE IF NOT EXISTS clientes(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nif VARCHAR(9) UNIQUE,
  nombre VARCHAR(20) NOT NULL,
  apellido1 VARCHAR(15) NOT NULL,
  apellido2 VARCHAR(15),
  razon_social VARCHAR(50),
  descuento INTEGER,
  obsoleto ENUM('YES', 'NO') NOT NULL
);

CREATE TABLE IF NOT EXISTS articulos(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  cod_articulo VARCHAR(10) UNIQUE,
  descripcion VARCHAR(100) NOT NULL,
  precio DOUBLE(6,2) NOT NULL,
  stock INTEGER NOT NULL,
  iva INTEGER NOT NULL,
  descuento INTEGER,
  obsoleto ENUM('YES', 'NO') NOT NULL
);

CREATE TABLE IF NOT EXISTS facturas(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  cliente INTEGER NOT NULL,
  fecha_factura DATE NOT NULL,
  num_factura INTEGER NOT NULL,
  calle VARCHAR(50) NOT NULL,
  numero INTEGER NOT NULL,
  planta INTEGER,
  letra VARCHAR(1),
  cod_postal VARCHAR(5) NOT NULL,
  localidad VARCHAR(50) NOT NULL,
  provincia VARCHAR(50) NOT NULL,
  obsoleto ENUM('YES', 'NO') NOT NULL,
  FOREIGN KEY (cliente) REFERENCES clientes (id)
);

CREATE TABLE IF NOT EXISTS detalles_factura(
  num_detalle INTEGER NOT NULL,
  factura INTEGER NOT NULL,
  articulo INTEGER NOT NULL,
  unidades INTEGER NOT NULL,
  precio DOUBLE NOT NULL,
  descuento INTEGER,
  iva INTEGER NOT NULL,
  obsoleto ENUM('YES', 'NO') NOT NULL,
  FOREIGN KEY (factura) REFERENCES facturas (id),
  FOREIGN KEY (articulo) REFERENCES articulos (id),
  PRIMARY KEY (num_detalle, factura)
);
