package com.escom.tokinn.model;

public class FacebookModel {
	private Long idUsuario;
	private String idFacebook;
	
	public FacebookModel() {
		super();
	}
	
	public FacebookModel(Long idUsuario, String idFacebook) {
		super();
		this.idUsuario = idUsuario;
		this.idFacebook = idFacebook;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getIdFacebook() {
		return idFacebook;
	}
	
	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}
	
}
