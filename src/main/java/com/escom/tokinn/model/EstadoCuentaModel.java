package com.escom.tokinn.model;

import java.util.Date;

public class EstadoCuentaModel {
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaExpedicion;
	private String key;
	private String tokenAutorizacion;
	private String firma;
	
	public EstadoCuentaModel() {
		super();
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTokenAutorizacion() {
		return tokenAutorizacion;
	}

	public void setTokenAutorizacion(String tokenAutorizacion) {
		this.tokenAutorizacion = tokenAutorizacion;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}
	
}
