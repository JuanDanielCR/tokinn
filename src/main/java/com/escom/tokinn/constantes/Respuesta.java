package com.escom.tokinn.constantes;

public class Respuesta<T> {

	private Integer codigoRespuesta;
	private String mensaje;
	private T entidad;
	
	public Respuesta() {
		super();
		this.codigoRespuesta = CodigoRespuesta.OK;
	}
	
	public Respuesta(Integer codigoRespuesta, String mensaje, T entidad) {
		super();
		this.codigoRespuesta = codigoRespuesta;
		this.mensaje = mensaje;
		this.entidad = entidad;
	}
	
	public Integer getCodigoRespuesta() {
		return codigoRespuesta;
	}
	
	public void setCodigoRespuesta(Integer codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public T getEntidad() {
		return entidad;
	}
	
	public void setEntidad(T entidad) {
		this.entidad = entidad;
	}
	
}
