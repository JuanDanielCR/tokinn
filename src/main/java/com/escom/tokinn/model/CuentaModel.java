package com.escom.tokinn.model;

import java.util.Date;

import com.escom.tokinn.entity.TipoCuenta;
import com.escom.tokinn.entity.Usuario;

public class CuentaModel {
	private String numeroCuenta;
	private String cvv;
	private Date fechaVencimiento;
	private TipoCuenta tipoCuenta;
	private Usuario usuario;
	
	public CuentaModel() {
		super();
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

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
