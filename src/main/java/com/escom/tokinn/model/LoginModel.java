package com.escom.tokinn.model;

public class LoginModel {
	private String numeroTarjeta;
	private String cvv;
	
	public LoginModel() {
		super();
	}

	public LoginModel(String numeroTarjeta, String cvv) {
		super();
		this.numeroTarjeta = numeroTarjeta;
		this.cvv = cvv;
	}
	
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	
}
