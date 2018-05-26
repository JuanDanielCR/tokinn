package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class EstadoCuenta {
	@Id
	@GeneratedValue
	@Column(name="idEstadoCuenta")
	private Long idEstadoCuenta;
	
	@Column(name="")
	private Date fechaInicio;
	
}
