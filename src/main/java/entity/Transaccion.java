package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class Transaccion {
	@Id
	@GeneratedValue
	@Column(name="idTransaccion")
	private Long idTransaccion;
	
	@Column(name="nombreItem")
	private String nombreItem;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="precioUnitario")
	private Double precioUnitario;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="fechaTransaccion")
	private Date fechaTransaccion;
	
	@Column(name="tokenAutorizacion")
	private String tokenAutorizacion;
	
	@Column(name="producto")
	private String producto;
	
	@Column(name="amount")
	private Double amount;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idCuenta")
	private Cuenta cuenta;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idTipo")
	private TipoTransaccion tipoTransaccion;
	
	public Transaccion() {	
	}
	
	public Transaccion(Long idTransaccion, String nombreItem, String descripcion, Double precioUnitario,
			Integer cantidad, Date fechaTransaccion, String tokenAutorizacion, String producto, Double amount) {
		super();
		this.idTransaccion = idTransaccion;
		this.nombreItem = nombreItem;
		this.descripcion = descripcion;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.fechaTransaccion = fechaTransaccion;
		this.tokenAutorizacion = tokenAutorizacion;
		this.producto = producto;
		this.amount = amount;
	}

	public Long getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Long idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getNombreItem() {
		return nombreItem;
	}

	public void setNombreItem(String nombreItem) {
		this.nombreItem = nombreItem;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getTokenAutorizacion() {
		return tokenAutorizacion;
	}

	public void setTokenAutorizacion(String tokenAutorizacion) {
		this.tokenAutorizacion = tokenAutorizacion;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
