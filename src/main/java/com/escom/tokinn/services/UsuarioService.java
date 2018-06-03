package com.escom.tokinn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.escom.tokinn.entity.Cuenta;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.repository.CuentaRepository;
import com.escom.tokinn.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioService {
	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	@Qualifier("cuentaRepository")
	private CuentaRepository cuentaRepository;
	
	private BCryptPasswordEncoder bpe;
	
	
	public List<Usuario> findUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario registrarUsuario(Usuario entidad) {
		bpe = new BCryptPasswordEncoder();
		entidad.setPassword(bpe.encode(entidad.getPassword()));
		entidad.setHasToken(Boolean.FALSE);
		return usuarioRepository.save(entidad);
	}
	
	public Usuario findUsuarioByCuenta(String numeroTarjeta, String cvv) {
		Usuario usuario = new Usuario();
		Cuenta cuenta = cuentaRepository.findByNumeroCuentaAndCvv(numeroTarjeta, cvv);
		if(cuenta != null) {
			usuario = cuenta.getUsuario();
		}
		return usuario;
	}
}
