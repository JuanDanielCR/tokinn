package com.escom.tokinn.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="cuenta")
public class Cuenta implements Serializable{
	
	private static final long serialVersionUID = 7908666802705096666L;

	@Id
	@GeneratedValue
	@Column(name="id_cuenta")
	private Long idCuenta;
	
	@Column(name="numero_cuenta")
	private String numeroCuenta;
	
	@Column(name="cvv")
	private String cvv;
	
	@Column(name="fecha_vencimiento")
	private Date fechaVencimiento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_tipo")
	private TipoCuenta tipoCuenta;
	
	@OneToMany(mappedBy = "cuenta", fetch=FetchType.EAGER)
	private List<Transaccion> transacciones;

	public Cuenta() {	
	}
	
	public Cuenta(Long idCuenta, String numeroCuenta, String cvv, Date fechaVencimiento, Usuario usuario,
			TipoCuenta tipoCuenta, List<Transaccion> transacciones) {
		super();
		this.idCuenta = idCuenta;
		this.numeroCuenta = numeroCuenta;
		this.cvv = cvv;
		this.fechaVencimiento = fechaVencimiento;
		this.usuario = usuario;
		this.tipoCuenta = tipoCuenta;
		this.transacciones = transacciones;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}
	
}
