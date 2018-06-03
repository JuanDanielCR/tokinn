package com.escom.tokinn.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="estado_cuenta")
public class EstadoCuenta implements Serializable{

	private static final long serialVersionUID = -8039392082856829040L;

	@Id
	@SequenceGenerator(name = "t04_estado_seq", sequenceName = "t04_estado_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t04_estado_seq")
	@Column(name="id_estado_cuenta")
	private Long idEstadoCuenta;
	
	@Column(name="fecha_inicio")
	private Date fechaInicio;
	
	@Column(name="fecha_fin")
	private Date fechaFin;
	
	@Column(name="fecha_expedicion")
	private Date fechaExpedicion;
	
	@Column(name="key_estado")
	private String key;
	
	@Column(name="token_autorizacion")
	private String tokenAutorizacion;
	
	@Column(name="firma")
	private String firma;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cuenta")
	private Cuenta cuentaEstado;

	public EstadoCuenta() {
		super();
	}

	public EstadoCuenta(Long idEstadoCuenta, Date fechaInicio, Date fechaFin, Date fechaExpedicion, String key,
			String tokenAutorizacion, String firma, Cuenta cuenta) {
		super();
		this.idEstadoCuenta = idEstadoCuenta;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaExpedicion = fechaExpedicion;
		this.key = key;
		this.tokenAutorizacion = tokenAutorizacion;
		this.firma = firma;
		this.cuentaEstado = cuenta;
	}

	public Long getIdEstadoCuenta() {
		return idEstadoCuenta;
	}

	public void setIdEstadoCuenta(Long idEstadoCuenta) {
		this.idEstadoCuenta = idEstadoCuenta;
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

	public Cuenta getCuentaEstado() {
		return cuentaEstado;
	}

	public void setCuentaEstado(Cuenta cuentaEstado) {
		this.cuentaEstado = cuentaEstado;
	}

	
}
