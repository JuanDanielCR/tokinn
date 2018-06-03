package com.escom.tokinn.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipo_transaccion")
public class TipoTransaccion implements Serializable {
	
	private static final long serialVersionUID = -2448367112502981687L;

	@Id
	@GeneratedValue
	@Column(name="id_tipo")
	private Long idTipoTransaccion;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripcion")
	private String descripcion;

	@OneToMany(mappedBy = "tipoTransaccion", fetch=FetchType.EAGER)
	private List<Transaccion> transacciones;
	
	public TipoTransaccion() {
	}

	public TipoTransaccion(Long idTipoTransaccion, String nombre, String descripcion, List<Transaccion> transacciones) {
		super();
		this.idTipoTransaccion = idTipoTransaccion;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.transacciones = transacciones;
	}

	public Long getIdTipoTransaccion() {
		return idTipoTransaccion;
	}

	public void setIdTipoTransaccion(Long idTipoTransaccion) {
		this.idTipoTransaccion = idTipoTransaccion;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
