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
public class Usuario {
	@Id
	@GeneratedValue
	@Column(name="idUsuario")
	private Long idUsuario;
	
	@Column(name="idFacebook", length=45)
	private String idFacebook;
	
	@Column(name="password", length=45)
	private String password;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellidoPaterno")
	private String apellidoPaterno;
	
	@Column(name="apellidoMaterno")
	private String apellidoMaterno;
	
	@Column(name="email", unique=true)
	private String email;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="telefono")
	private String telefono;
	
	@OneToMany(mappedBy = "usuario", fetch=FetchType.EAGER)
	private List<Cuenta> cuentas;
	
	public Usuario() {
		//Deafult constructor
	}

	public Usuario(Long idUsuario, String idFacebook, String password, String nombre, String apellidoPaterno,
			String apellidoMaterno, String email, String direccion, String telefono, List<Cuenta> cuentas) {
		super();
		this.idUsuario = idUsuario;
		this.idFacebook = idFacebook;
		this.password = password;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
		this.cuentas = cuentas;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
}
