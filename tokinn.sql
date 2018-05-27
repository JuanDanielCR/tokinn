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
