CREATE DATABASE tokinn
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Mexico.1252'
       LC_CTYPE = 'Spanish_Mexico.1252'
       CONNECTION LIMIT = -1;

USE tokinn;
--cuenta
CREATE TABLE public.cuenta() WITH ( OIDS=FALSE );
ALTER TABLE public.cuenta OWNER TO postgres;
--estado_cuenta
CREATE TABLE public.estado_cuenta() WITH ( OIDS=FALSE );
ALTER TABLE public.estado_cuenta OWNER TO postgres;
--tipo_cuenta
CREATE TABLE public.tipo_cuenta() WITH ( OIDS=FALSE );
ALTER TABLE public.tipo_cuenta OWNER TO postgres;
--tipo_transaccion
CREATE TABLE public.tipo_transaccion() WITH ( OIDS=FALSE );
ALTER TABLE public.tipo_transaccion OWNER TO postgres;
--transaccion
CREATE TABLE public.transaccion() WITH ( OIDS=FALSE );
ALTER TABLE public.transaccion OWNER TO postgres;
--usuario
CREATE TABLE public.usuario() WITH ( OIDS=FALSE );
ALTER TABLE public.usuario OWNER TO postgres;


--INSERT usuario
INSERT INTO public.usuario(id_usuario, id_facebook, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (1, null, 'Castillo', 'Reyes', 'Juan Daniel', 'Calle Orquídeas M26 LT.37 No 32 Coacalco Estado de México', 'castilloreyesjuan@gmail.com', 'admin123', '3313979662');
INSERT INTO public.usuario(id_usuario, id_facebook, apellido_paterno, apellido_materno, nombre, direccion, email, pass, telefono)VALUES (2, null, 'Castillo', 'Reyes', 'Eduardo Armando', 'Calle Orquídeas M26 LT.37 No 32 Coacalco Estado de México', 'armandocreyes@gmail.com', 'prueba123', '5526471243');
  
