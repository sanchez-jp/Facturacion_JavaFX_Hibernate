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

/* Inserción de tuplas en la tabla clientes */
INSERT INTO `clientes` (`id`, `nif`, `nombre`, `apellido1`, `apellido2`, `razon_social`, `descuento`, `obsoleto`) 
VALUES  (NULL, '08894832L', 'Víctor Alfonso', 'Saavedra', 'Caldera', 'ALUMASA S.A.', '10', 'NO'), 
	(NULL, '80063598T', 'Juan Pedro', 'Sánchez', 'Álvarez', 'INDRA', '20', 'NO'), 
        (NULL, '91992228W', 'Luís', 'Ortíz', 'Moreno', 'L&C Soluciones', '0', 'NO'), 
        (NULL, '05534591D', 'Jesús', 'Romano', 'Barril', 'Romano Electricidad', '8', 'YES'), 
        (NULL, '49427889S', 'Ana', 'Rodríguez', 'Trejo', 'Zapatú', '10', 'NO'), 
        (NULL, '36699372C', 'Marisa', 'Rosales', 'Guzmán', 'IDEA MONTIJO', '8', 'NO'), 
        (NULL, '86569025S', 'Isabel', 'Letellez', 'Martín', 'Toledana de aceros', '15', 'NO'), 
        (NULL, '65931297A', 'Jaime', 'Borromeo', 'Sanchidrián', 'Academia Jaimito', '0', 'YES'), 
        (NULL, '01837822F', 'Susana', 'Díaz', 'Díaz', 'IberTech', '15', 'NO'), 
        (NULL, '86420667F', 'Dimas', 'De La Fuente', 'Peralta', 'Línea D4', '10', 'NO'), 
        (NULL, '71372750R', 'Gustavo', 'Fring', '', 'Los Pollos Hermanos', '21', 'NO'), 
        (NULL, '75817693X', 'Juan', 'Sánchez', 'Aguilera', 'Panadería Aguilera', '21', 'YES');

/* Inserción de tuplas en la tabla artículos */
INSERT INTO `articulos` (`id`, `cod_articulo`, `descripcion`, `precio`, `stock`, `iva`, `descuento`, `obsoleto`) 
VALUES  (NULL, 'A01ACADT', 'Artículo de prueba 11', '15.60', '100', '21', '10', 'NO'), 
        (NULL, 'A02ACADT', 'Artículo de prueba 12', '35.70', '45', '21', '0', 'NO'), 
        (NULL, 'A03ACADT', 'Artículo de prueba 3', '12.60', '600', '21', '0', 'NO'), 
        (NULL, 'A04ACADT', 'Artículo de prueba 4', '6', '500', '21', '12', 'NO'), 
        (NULL, 'A05ACADT', 'Artículo de prueba 5', '150.50', '0', '21', '10', 'YES'), 
        (NULL, 'A06ACADT', 'Artículo de prueba 6', '50', '0', '21', '5', 'YES'), 
        (NULL, 'A07ACADT', 'Artículo de prueba 7', '0.80', '120', '21', '0', 'NO'), 
        (NULL, 'A08ACADT', 'Artículo de prueba 8', '70.20', '0', '21', '10', 'YES'), 
        (NULL, 'A09ACADT', 'Artículo de prueba 9', '12.60', '0', '21', '0', 'YES'), 
        (NULL, 'A10ACADT', 'Artículo de prueba 10', '20.30', '10', '21', '5', 'NO');
