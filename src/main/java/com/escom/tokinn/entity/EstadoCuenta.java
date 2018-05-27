package com.escom.tokinn.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estado_cuenta")
public class EstadoCuenta implements Serializable{

	private static final long serialVersionUID = -8039392082856829040L;

	@Id
	@GeneratedValue
	@Column(name="idEstadoCuenta")
	private Long idEstadoCuenta;
	
	@Column(name="")
	private Date fechaInicio;
	
}
