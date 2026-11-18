CREATE DATABASE agencia_de_autos;

USE agencia_de_autos;

CREATE TABLE empleados (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre VARCHAR(80) NOT NULL,
	correo VARCHAR(80) UNIQUE NOT NULL,
	contrasena VARCHAR(30) NOT NULL,
	rol VARCHAR(50) NOT NULL);
    insert into empleados values(1,"asdasd", "dasdasd", "ss", "empleado");
CREATE TABLE clientes (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre VARCHAR (100) NOT NULL,
	telefono VARCHAR (20) NOT NULL);
    insert into clientes values(1,"asdasd", "8446677505");
  select * from clientes;
CREATE TABLE autos (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	modelo VARCHAR(80) NOT NULL,
	marca VARCHAR(30) NOT NULL,
	ano INT NOT NULL,
	caracteristicas VARCHAR(250) NOT NULL);
    insert into autos values(1,"asdasd", "dasdasd", "2005", "uysdgyfubsdjbsjbcsdvbchjbsdhc");

CREATE TABLE servicios (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	folio VARCHAR(80) NOT NULL,
	id_cliente INT NOT NULL,
	id_auto INT NOT NULL,
	id_empleado INT NOT NULL,
	fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	estatus VARCHAR(30) DEFAULT "En espera",
	fecha_siguiente_servicio DATE,
	CONSTRAINT fk_idCliente FOREIGN KEY (id_cliente) REFERENCES clientes (id),
	CONSTRAINT fk_idAuto FOREIGN KEY (id_auto) REFERENCES autos (id),
	CONSTRAINT fk_idEmpleado FOREIGN KEY (id_empleado) REFERENCES empleados (id));

CREATE TABLE refacciones (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 	nombre VARCHAR(100) NOT NULL,
	precio DOUBLE NOT NULL);
   insert into refacciones values(1,"ss", 1500);
CREATE TABLE refaccion_por_servicio (
    id_servicio INT NOT NULL,
    id_refaccion INT NOT NULL,
    CONSTRAINT pk_refaccionPorServicio PRIMARY KEY (id_servicio, id_refaccion),
    CONSTRAINT fk_idServicio FOREIGN KEY (id_servicio) REFERENCES servicios(id),
    CONSTRAINT fk_idRefaccion FOREIGN KEY (id_refaccion) REFERENCES refacciones(id)
);
alter table servicios add persona_entrega varchar(100);
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE refaccion_por_servicio; TRUNCATE TABLE servicios; TRUNCATE TABLE refacciones;
TRUNCATE TABLE autos; TRUNCATE TABLE clientes; TRUNCATE TABLE empleados;
SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO empleados VALUES (1,'Carlos Gonzalez','carlos@agencia.com','1234','admin'),(2,'Ana Torres','ana@agencia.com','1234','empleado');
INSERT INTO clientes VALUES (1,'Juan Pérez','8441112233'),(2,'María López','8442223344'),(3,'Luis Ramos','8443334455'),(4,'Sofía Díaz','8444445566');
INSERT INTO autos VALUES (1,'Versa','Nissan',2019,'Gris'),(2,'Corolla','Toyota',2021,'Blanco'),(3,'Focus','Ford',2017,'Rojo'),(4,'Jetta','VW',2020,'Negro');
INSERT INTO refacciones VALUES (1,'Filtro de aceite',250),(2,'Balatas delanteras',980),(3,'Bujías NGK',640),(4,'Batería 12V',2100);
INSERT INTO servicios (folio,id_cliente,id_auto,id_empleado,fecha_ingreso,estatus,fecha_siguiente_servicio,persona_entrega) VALUES
('SRV-001',1,1,1,DATE_SUB(NOW(),INTERVAL 150 DAY),'Finalizado',DATE_ADD(CURDATE(),INTERVAL 20 DAY),'Juan Pérez'),
('SRV-002',2,2,2,DATE_SUB(NOW(),INTERVAL 120 DAY),'Finalizado',NULL,'María López'),
('SRV-003',3,3,1,DATE_SUB(NOW(),INTERVAL 90 DAY),'Finalizado',NULL,'Luis Ramos'),
('SRV-004',1,1,2,DATE_SUB(NOW(),INTERVAL 60 DAY),'Finalizado',DATE_ADD(CURDATE(),INTERVAL 5 DAY),'Juan Pérez'),
('SRV-005',4,4,1,DATE_SUB(NOW(),INTERVAL 40 DAY),'En proceso',DATE_ADD(CURDATE(),INTERVAL 40 DAY),'Sofía Díaz'),
('SRV-006',2,2,2,DATE_SUB(NOW(),INTERVAL 20 DAY),'En proceso',NULL,'María López'),
('SRV-007',3,3,1,DATE_SUB(NOW(),INTERVAL 3 DAY),'En espera',DATE_ADD(CURDATE(),INTERVAL 60 DAY),'Luis Ramos'),
('SRV-008',4,4,2,NOW(),'En espera',NULL,'Sofía Díaz');
INSERT INTO refaccion_por_servicio VALUES (1,1),(1,3),(2,1),(2,2),(3,2),(4,1),(5,4),(6,1),(6,3);
