package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class TipoTransaccion {
	@Id
	@GeneratedValue
	@Column(name="idTipo")
	private Long idTipoTransaccion;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripcion")
	private Long descripcion;

	@OneToMany(mappedBy = "tipoTransaccion", fetch=FetchType.EAGER)
	private List<Transaccion> transacciones;
	
	public TipoTransaccion() {
	}
	
	public TipoTransaccion(Long idTipoTransaccion, String nombre, Long descripcion, List<Transaccion> transacciones) {
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

	public Long getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(Long descripcion) {
		this.descripcion = descripcion;
	}
	
}
