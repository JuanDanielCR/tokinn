package entity;

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
@Table(name="tipoCuenta")
public class TipoCuenta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7635690072078123476L;

	@Id
	@GeneratedValue
	@Column(name="idTipo")
	private Long idTipoCuenta;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripcion")
	private Long descripcion;

	@OneToMany(mappedBy = "tipoCuenta", fetch=FetchType.EAGER)
	private List<Cuenta> cuentas;
	
	public TipoCuenta() {
		
	}

	public TipoCuenta(Long idTipoCuenta, String nombre, Long descripcion, List<Cuenta> cuentas) {
		super();
		this.idTipoCuenta = idTipoCuenta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cuentas = cuentas;
	}

	public Long getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(Long idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(Long descripcion) {
		this.descripcion = descripcion;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
}
