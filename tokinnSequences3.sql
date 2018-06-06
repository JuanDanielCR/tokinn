CREATE DATABASE tokinn
WITH OWNER = postgres
ENCODING = 'UTF8'
TABLESPACE = pg_default
LC_COLLATE = 'Spanish_Mexico.1252'
LC_CTYPE = 'Spanish_Mexico.1252'
CONNECTION LIMIT = -1;

USE tokinn;
-- Table: public.usuario
-- DROP TABLE public.usuario;
CREATE TABLE public.usuario
(
  id_usuario bigint NOT NULL,
  apellido_materno character varying(255),
  apellido_paterno character varying(255),
  direccion character varying(255),
  email character varying(255),
  has_token_activated boolean,
  id_facebook character varying(255),
  id_messenger character varying(255),
  nombre character varying(255),
  pass character varying(255),
  telefono character varying(255),
  CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario),
  CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.usuario
  OWNER TO postgres;


-- Table: public.tipo_cuenta
-- DROP TABLE public.tipo_cuenta;
CREATE TABLE public.tipo_cuenta
(
  id_tipo bigint NOT NULL,
  descripcion character varying(255),
  nombre character varying(255),
  CONSTRAINT tipo_cuenta_pkey PRIMARY KEY (id_tipo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tipo_cuenta
  OWNER TO postgres;


-- Table: public.tipo_transaccion
-- DROP TABLE public.tipo_transaccion;
CREATE TABLE public.tipo_transaccion
(
  id_tipo bigint NOT NULL,
  descripcion character varying(255),
  nombre character varying(255),
  CONSTRAINT tipo_transaccion_pkey PRIMARY KEY (id_tipo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tipo_transaccion
  OWNER TO postgres;


-- Table: public.cuenta
-- DROP TABLE public.cuenta;
CREATE TABLE public.cuenta
(
  id_cuenta bigint NOT NULL,
  cvv character varying(255),
  fecha_vencimiento timestamp without time zone,
  numero_cuenta character varying(255),
  id_tipo bigint,
  id_usuario bigint,
  CONSTRAINT cuenta_pkey PRIMARY KEY (id_cuenta),
  CONSTRAINT fkpuu4iaiq2yhyvn4ebxcj9uefk FOREIGN KEY (id_usuario)
      REFERENCES public.usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fks0fteuw2u8i8ijm271y0u258v FOREIGN KEY (id_tipo)
      REFERENCES public.tipo_cuenta (id_tipo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.cuenta
  OWNER TO postgres;


-- Table: public.estado_cuenta
-- DROP TABLE public.estado_cuenta;
CREATE TABLE public.estado_cuenta
(
  id_estado_cuenta bigint NOT NULL,
  fecha_expedicion timestamp without time zone,
  fecha_fin timestamp without time zone,
  fecha_inicio timestamp without time zone,
  firma character varying(255),
  key_estado character varying(255),
  token_autorizacion character varying(255),
  id_cuenta bigint,
  CONSTRAINT estado_cuenta_pkey PRIMARY KEY (id_estado_cuenta),
  CONSTRAINT fkounfxb5bx6k3y3wdscohfxd57 FOREIGN KEY (id_cuenta)
      REFERENCES public.cuenta (id_cuenta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.estado_cuenta
  OWNER TO postgres;



-- Table: public.transaccion
-- DROP TABLE public.transaccion;
CREATE TABLE public.transaccion
(
  id_transaccion bigint NOT NULL,
  amount double precision,
  cantidad integer,
  descripcion character varying(255),
  fecha_transaccion timestamp without time zone,
  nombre_item character varying(255),
  precio_unitario double precision,
  producto character varying(255),
  token_autorizacion character varying(255),
  id_cuenta bigint,
  id_tipo bigint,
  CONSTRAINT transaccion_pkey PRIMARY KEY (id_transaccion),
  CONSTRAINT fk7iw238aslgnasmh5bm56kd3ew FOREIGN KEY (id_tipo)
      REFERENCES public.tipo_transaccion (id_tipo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrc7n8sdxm6940u9xupxm4bsia FOREIGN KEY (id_cuenta)
      REFERENCES public.cuenta (id_cuenta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.transaccion
  OWNER TO postgres;

-- Sequences 

-- Sequence: public.hibernate_sequence
-- DROP SEQUENCE public.hibernate_sequence;

CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;

-- Sequence: public.t01_usuario_seq
-- DROP SEQUENCE public.t01_usuario_seq;
CREATE SEQUENCE public.t01_usuario_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.t01_usuario_seq
  OWNER TO postgres;

-- Sequence: public.t02_cuenta_seq
-- DROP SEQUENCE public.t02_cuenta_seq;
CREATE SEQUENCE public.t02_cuenta_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.t02_cuenta_seq
  OWNER TO postgres;

-- Sequence: public.t03_transaccion_seq
-- DROP SEQUENCE public.t03_transaccion_seq;
CREATE SEQUENCE public.t03_transaccion_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.t03_transaccion_seq
  OWNER TO postgres;

-- Sequence: public.t04_estado_seq
-- DROP SEQUENCE public.t04_estado_seq;
CREATE SEQUENCE public.t04_estado_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.t04_estado_seq
  OWNER TO postgres;



--INSERT usuario
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Castillo', 'Reyes', 'Juan Daniel', 'Calle Orquídeas M26 LT.37 No 32 Coacalco Estado de México', 'castilloreyesjuan@gmail.com', 'admin123', '3313979662');
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Castillo', 'Reyes', 'Eduardo Armando', 'Calle Orquídeas M26 LT.37 No 32 Coacalco Estado de México', 'armandocreyes@gmail.com', 'prueba123', '5526471243');
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Jiménez', 'Chávez', 'Luis Gerardo', 'Sur 14 No 10 San Agustin 1a Secc Ecatepec de Morelos', 'lgjc1im11@gmail.com', 'luis123', '5523446668');
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Nava', 'Rivas', 'Ana Paola', 'Calle Venezuela M32 LT.7 No 25 Ojo de Agua Estado de México', 'ananava1996@gmail.com', 'ana123', '5552698546');
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Duran', 'Martinez', 'Josue', 'Calle 22 M5 LT.3 Iztapalapa Estado de México', 'jozzduranm@gmail.com', 'josue123', '5513598785');
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Lopez', 'Orozco', 'Diego Efrain', 'Avenida Eduardo Molina M2 LT.5 Gustavo A. Madero Ciudad de México', 'lopord@gmail.com', 'diego123', '5546568912');
INSERT INTO public.usuario(id_usuario, id_facebook, id_messenger, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (nextval('t01_usuario_seq'), null, null, 'Luna', 'Reyes', 'Eduardo', 'Nuevo paseo de Louisiana M15 LT.10 Gustavo A. Madero Ciudad de México', 'eduardol@gmail.com', 'eduardo123', '5510527496');
--INSERT tipo_cuenta
INSERT INTO public.tipo_cuenta(id_tipo, descripcion, nombre) VALUES (1, 'Estos son depósitos corrientes que pueden ser utilizados en cualquier momento a voluntad y requerimiento del titular de la cuenta.', 'Cuenta basica');
INSERT INTO public.tipo_cuenta(id_tipo, descripcion, nombre) VALUES (2, 'Los depósitos de ahorros es aquel tipo de depósito que se consigna en las entidades financieras, se mantienen en poder y a disposición de dicha entidad por períodos más largos que los depósitos de cuentas corriente.', 'Cuenta de ahorro');
--INSERT INTO public.tipo_cuenta(id_tipo, descripcion, nombre) VALUES (3, 'Son depósitos que se formalizan entre el cliente y el banco por medio de un documento o certificado; se pactan por un monto y plazo determinado', 'Depósitos a largo plazo');
--INSERT INTO public.tipo_cuenta(id_tipo, descripcion, nombre) VALUES (4, 'Esta clase de depósito da lugar a las cuentas de ahorro, las cuales pueden abrirse por cantidades que son diferentes para los diversos bancos y que pueden ser muy pequeñas para algunos de ellos', 'Cuenta de ahorro corriente');
--INSERT tipo_transaccion
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (1, 'Es una aportación de dinero por parte del cliente a la entidad financiera cuyo importe debe ser devuelto en la forma pactada', 'Depóito bancario');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (2, 'Es un contrato por el cual una entidad de crédito (prestamista) se obliga a entregar al cliente (prestatario) una determinada cantidad de dinero, viéndose este último obligado a devolverlo de una vez o en varios plazos, además de pagar unos intereses', 'Préstamo');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (3, 'Es una operación por la cual la entidad de crédito se obliga a conceder crédito al cliente  a cambio de una contraprestación que incluye normalmente una comisión y unos intereses', 'Apertura crédito');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (4, 'es aquel en donde el arrendador adquiere un bien para ceder su uso durante un plazo de tiempo al arrendatario el cual está obligado a pagar una cantidad suficiente para amortizar el valor de adquisición del bien y los gastos aplicables.', 'Contrato de arrendamiento');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (5, 'es un pacto entre un empresario y una entidad denominada de “factoring” la cual ofrece dos servicios, administración de cobros y financiación.', 'Contrato de facturación');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (6, 'Tratan de traspasar una determinada cantidad de dinero de una cuenta bancaria a otra ejecutándose mediante el acuerdo en la primera y en el abono en la segunda cuenta.', 'Transferencia bancaria');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (7, 'Pago realizado en cualquier sucursal', 'Pago bancario');
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (8, 'Pago realizado en cualquier pagina en internet', 'Pago bancario online');
--INSERT cuenta
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 235, TIMESTAMP '2019-05-16 15:36:38', 032180000118359719, 1, 1);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 654, TIMESTAMP '2019-06-20 15:36:38', 032180913518359135, 1, 2);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 984, TIMESTAMP '2020-01-03 15:36:38', 032180001335495531, 2, 3);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 154, TIMESTAMP '2020-08-08 15:36:38', 032180000650695412, 2, 4);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 951, TIMESTAMP '2021-10-03 15:36:38', 032180000116498502, 1, 5);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 324, TIMESTAMP '2020-05-30 15:36:38', 032180000130551698, 1, 6);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 254, TIMESTAMP '2021-11-20 15:36:38', 032180000164829411, 2, 7);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 756, TIMESTAMP '2022-07-16 15:36:38', 032180000168716543, 2, 1);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 214, TIMESTAMP '2020-03-30 15:36:38', 032180000198740654, 1, 2);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 465, TIMESTAMP '2023-04-14 15:36:38', 032180000165412354, 1, 3);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 682, TIMESTAMP '2022-12-26 15:36:38', 032180000105456575, 2, 4);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 256, TIMESTAMP '2020-01-31 15:36:38', 032180000105565432, 2, 5);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 924, TIMESTAMP '2021-08-20 15:36:38', 032180000106874357, 1, 6);
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (nextval('t02_cuenta_seq'), 214, TIMESTAMP '2022-09-05 15:36:38', 032180000106857654, 1, 7);
--INSERT transaccion
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 3000.0, 1, 'Deposito bancario CitiBanamex', TIMESTAMP '2017-04-14 15:36:38', 'Deposito', 3000.0, null, null, 1, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 8000.0, 2, 'Prestamo bancario', TIMESTAMP '2017-04-14 15:36:38', 'Prestamo', 4000.0, null, null, 2, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 36.0, 3, 'Refresco de Cola', TIMESTAMP '2017-04-15 15:36:38', 'Coca Cola 600 ml', 12.0, 'Refresco', null, 7, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 900.0, 1, 'Juego de video para consola Xbox', TIMESTAMP '2017-04-16 15:36:38', 'Halo Reach', 900.0, 'Juego Xbox', null, 8, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 780.0, 1, 'Contrato de facturacion', TIMESTAMP '2017-04-17 15:36:38', 'Contrato facturacion', 780.0, null, null, 5, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 4600.0, 1, 'Transferencia bancaria CitiBanamex', TIMESTAMP '2017-04-18 15:36:38', 'Transferencia', 4600.0, null, null, 6, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 390.0, 1, 'Deposito bancario Bancomer', TIMESTAMP '2017-04-19 15:36:38', 'Deposito', 390.0, null, null, 1, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 6890.0, 1, 'Prestamo Bancario', TIMESTAMP '2017-04-20 15:36:38', 'Prestamo', 6890.0, null, null, 2, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 350.0, 1, 'Apertura cedito', TIMESTAMP '2017-04-21 15:36:38', 'Credito', 350.0, null, null, 3, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 400.0, 1, 'Contrato arrendamiento', TIMESTAMP '2017-04-22 15:36:38', 'Arrendamiento', 400.0, null, null, 4, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 10500, 2, 'Television LED', TIMESTAMP '2017-04-23 15:36:38', 'Sony 32"', 5250.0, 'Television', null, 7, 1);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 1200.0, 1, 'Control Xbox One Inalambrico', TIMESTAMP '2017-04-24 15:36:38', 'Control Xbox One', 1200.0, 'Control', null, 8, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 467.0, 1, 'Deposito bancario banamex', TIMESTAMP '2017-04-25 15:36:38', 'Deposito', 467.0, null, null, 1, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 12500.50, 1, 'Prestamo bancario', TIMESTAMP '2017-04-26 15:36:38', 'Prestamo', 12500.50, null, null, 2, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 500.0, 1, 'Apertura credito', TIMESTAMP '2017-04-27 15:36:38', 'Credito', 500, null, null, 3, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 500.0, 1, 'Contrato arrendamiento', TIMESTAMP '2017-04-28 15:36:38', 'Arrendamiento', 500.0, null, null, 4, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 420.0, 1, 'Mochila escolar', TIMESTAMP '2017-04-29 15:36:38', 'Mochila Willson', 20.0, 'Mochila', null, 7, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 782.0, 1, 'Transferencia bancaria bancomer', TIMESTAMP '2017-04-30 15:36:38', 'Tansferencia', 782.0, null, null, 6, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 3456.0, 1, 'Deposito bancario banorte', TIMESTAMP '2017-05-14 15:36:38', 'Deposito', 3456.0, null, null, 1, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 30988.0, 1, 'Prestamo bancario', TIMESTAMP '2017-05-15 15:36:38', 'Prestamo', 30988.0, null, null, 2, 2);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 249.0, 1, 'Daftpunk CD', TIMESTAMP '2017-05-16 15:36:38', 'Daftpunk Random Access Memories', 249.0, 'CD', null, 8, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 3847.0, 1, 'Contrato arrendamiento', TIMESTAMP '2017-05-17 15:36:38', 'Arrendamiento', 3847.0, null, null, 4, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 1200.0, 1, 'Contrato de facturacion', TIMESTAMP '2017-05-18 15:36:38', 'Contrato facturacion', 1200.0, null, null, 5, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 180.0, 1, 'Balon football soccer Nike', TIMESTAMP '2017-05-19 15:36:38', 'Balon Nike', 180.0, 'Balon', null, 7, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 5235.5, 1, 'Deposito bancario Guardadito', TIMESTAMP '2017-05-20 15:36:38', 'Deposito', 5235.5, null, null, 1, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 63453.0, 1, 'Prestamo bancario', TIMESTAMP '2017-05-21 15:36:38', 'Prestamo', 63453.0, null, null, 2, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 783.0, 1, 'Michael Jackson in Budapest DVD', TIMESTAMP '2017-05-22 15:36:38', 'Michael Jackson Budapest', 783.0, 'DVD', null, 8, 3);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 123.0, 1, 'Contrato de aarrendamiento', TIMESTAMP '2017-05-23 15:36:38', 'Contrato arrendamiento', 123.0, null, null, 4, 4);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 50.0, 5, 'Cuaderno Profesional de 100 hojas', TIMESTAMP '2017-05-24 15:36:38', 'Cuaderno Profesional', 50.0, 'Cuaderno', null, 7, 4);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 243.0, 1, 'Transferencia bancaria citibanamex', TIMESTAMP '2017-05-25 15:36:38', 'Transferencia', 243.0, null, null, 6, 4);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 2353.0, 1, 'Deposito bancario Santander', TIMESTAMP '2017-05-26 15:36:38','Deposito', 2353.0, null, null, 1, 4);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 320.0, 1, 'Pastel 3 kg chocolate', TIMESTAMP '2017-05-27 15:36:38', 'Pastel chocolate', 320.0, 'Pastel', null, 7, 4);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 5400.0, 2, 'Boleto the Weeknd Palacio de los deportes', TIMESTAMP '2017-05-28 15:36:38', 'Boleto the weeknd', 2700.0, 'Boleto', null, 8, 4);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 523.0, 1, 'Contrato de arrendamiento', TIMESTAMP '2017-05-29 15:36:38', 'Contrato arrendamiento', 523.0, null, null, 4, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 1150.0, 1, 'Membresia Xbox Live 12 meses', TIMESTAMP '2017-05-30 15:36:38', 'Membresia Xbox', 1150.0, 'Membresia', null, 8, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 665.0, 1, 'Transferencia bancaria Banorte', TIMESTAMP '2017-06-14 15:36:38', 'Transferencia', 665.0, null, null, 6, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 330.0, 1, 'Deposito bancario Santander', TIMESTAMP '2017-06-15 15:36:38', 'Deposito', 330.0, null, null, 1, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 5345.0, 1, 'Prestamo bancario', TIMESTAMP '2017-06-16 15:36:38', 'Prestamo', 5345.0, null, null, 2, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 423.0, 1, 'Apertura de credito', TIMESTAMP '2017-06-17 15:36:38', 'Credito', 423.0, null, null, 3, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 180.0, 1, 'Tequila reposado Jimador', TIMESTAMP '2017-06-18 15:36:38', 'Tequila Jimador', 180.0, 'Tequila', null, 7, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 554.0, 1, 'Contrato de facturacion', TIMESTAMP '2017-06-19 15:36:38', 'Contrato', 554.0, null, null, 5, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 320.0, 1, 'The weeknd KissLand CD', TIMESTAMP '2017-06-20 15:36:38', 'The weeknd KissLand', 320.0, 'CD', null, 8, 5);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 6436.0, 1, 'Deposito bancario Guardadito', TIMESTAMP '2017-06-21 15:36:38', 'Deposito', 6436.0, null, null, 1, 6);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 120.0, 1, 'Pizza Familiar Fud', TIMESTAMP '2017-06-22 15:36:38', 'Pizza Familiar', 120.0, 'Pizza', null, 7, 6);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 343.0, 1, 'Apertura de credito', TIMESTAMP '2017-06-23 15:36:38', 'Credito', 343.0, null, null, 3, 6);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 222.0, 1, 'Contrato de arrendamiento', TIMESTAMP '2017-06-24 15:36:38', 'Contrato', 222.0, null, null, 4, 7);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 233.0, 1, 'Contrato de facturacion', TIMESTAMP '2017-06-25 15:36:38', 'Contrato', 233.0, null, null, 5, 7);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 432.0, 1, 'Espejo cuerpo completo', TIMESTAMP '2017-06-26 15:36:38', 'Espejo', 432.0, 'Espejo', null, 7, 7);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 4565.0, 1, 'Deposito bancario citibanamex', TIMESTAMP '2017-06-27 15:36:38', 'Deposito', 4565.0, null, null, 1, 7);
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_tipo,
            id_cuenta)
    VALUES (nextval('t03_transaccion_seq'), 6456.0, 1, 'Prestamo bancario', TIMESTAMP '2017-06-28 15:36:38', 'Prestamo', 6456.0, null, null, 2, 7);

--INSERT estado_cuenta
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 1);

INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 2);

INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 3);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 4);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 5);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 6);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 7);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 8);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 9);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 10);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 11);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 12);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 13);
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (nextval('t04_estado_seq'), TIMESTAMP '2015-06-28 15:36:38', TIMESTAMP '2025-06-28 15:36:38', TIMESTAMP '2015-06-29 15:36:38', null, null, null, 14);