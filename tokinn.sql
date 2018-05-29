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
  id_facebook character varying(45),
  nombre character varying(255),
  pass character varying(45),
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
  descripcion bigint,
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
  descripcion bigint,
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


--INSERT usuario
INSERT INTO public.usuario(id_usuario, id_facebook, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (1, null, 'Castillo', 'Reyes', 'Juan Daniel', 'Calle Orquídeas M26 LT.37 No 32 Coacalco Estado de México', 'castilloreyesjuan@gmail.com', 'admin123', '3313979662');
INSERT INTO public.usuario(id_usuario, id_facebook, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (2, null, 'Castillo', 'Reyes', 'Eduardo Armando', 'Calle Orquídeas M26 LT.37 No 32 Coacalco Estado de México', 'armandocreyes@gmail.com', 'prueba123', '5526471243');
--INSERT tipo_cuenta
INSERT INTO public.tipo_cuenta(id_tipo, descripcion, nombre) VALUES (?, ?, ?);
--INSERT tipo_transaccion
INSERT INTO public.tipo_transaccion(id_tipo, descripcion, nombre) VALUES (?, ?, ?);
--INSERT cuenta
INSERT INTO public.cuenta(id_cuenta, cvv, fecha_vencimiento, numero_cuenta, id_tipo, id_usuario) VALUES (?, ?, ?, ?, ?, ?);
--INSERT transaccion
INSERT INTO public.transaccion(
            id_transaccion, amount, cantidad, descripcion, fecha_transaccion,
            nombre_item, precio_unitario, producto, token_autorizacion, id_cuenta,
            id_tipo)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

--INSERT estado_cuenta
INSERT INTO public.estado_cuenta(
            id_estado_cuenta, fecha_expedicion, fecha_fin, fecha_inicio,
            firma, key_estado, token_autorizacion, id_cuenta)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
