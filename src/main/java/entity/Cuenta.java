package entity;

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
	@Column(name="idCuenta")
	private Long idCuenta;
	
	@Column(name="cv")
	private String cv;
	
	@Column(name="fechaVencimiento")
	private Date fechaVencimiento;
	
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
	
	public Cuenta(Long idCuenta, String cv, Date fechaVencimiento, Usuario usuario, TipoCuenta tipoCuenta,
			List<Transaccion> transascciones) {
		super();
		this.idCuenta = idCuenta;
		this.cv = cv;
		this.fechaVencimiento = fechaVencimiento;
		this.usuario = usuario;
		this.tipoCuenta = tipoCuenta;
		this.transascciones = transascciones;
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

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public List<Transaccion> getTransascciones() {
		return transascciones;
	}

	public void setTransascciones(List<Transaccion> transascciones) {
		this.transascciones = transascciones;
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
