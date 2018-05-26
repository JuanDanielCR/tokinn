package entity;

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
public class Cuenta {
	@Id
	@GeneratedValue
	@Column(name="idCuenta")
	private Long idCuenta;
	
	@Column(name="cv")
	private String cv;
	
	@Column(name="fechaVencimiento")
	private Date nombre;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idUsuario")
	private TipoCuenta tipoCuenta;
	
	@OneToMany(mappedBy = "cuenta", fetch=FetchType.EAGER)
	private List<Transaccion> transascciones;

	public Cuenta() {	
	}
	
	public Cuenta(Long idCuenta, String cv, Date nombre, Usuario usuario, TipoCuenta tipoCuenta) {
		super();
		this.idCuenta = idCuenta;
		this.cv = cv;
		this.nombre = nombre;
		this.usuario = usuario;
		this.tipoCuenta = tipoCuenta;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public Date getNombre() {
		return nombre;
	}

	public void setNombre(Date nombre) {
		this.nombre = nombre;
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
	
}
