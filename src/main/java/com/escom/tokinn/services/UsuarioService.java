package com.escom.tokinn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.escom.tokinn.constantes.Bundle;
import com.escom.tokinn.constantes.CodigoRespuesta;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.constantes.Respuesta;
import com.escom.tokinn.entity.Cuenta;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.repository.CuentaRepository;
import com.escom.tokinn.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioService {
	
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
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
	
	public Respuesta<Usuario> verificarLogin(UsuarioModel model) {
		Respuesta<Usuario> respuesta = new Respuesta<Usuario>();
		Usuario entidad = usuarioRepository.findByIdUsuarioAndPassword(model.getId(), model.getPassword());
		if(entidad != null) {
			respuesta.setEntidad(entidad);
			//Tiene token activado
			if(entidad.getHasToken() == Boolean.TRUE 
					&& !tokenService.verifyToken(entidad.getIdFacebook(),NavigationConstants.TOKEN_INICIO, model.getToken())) {
				respuesta.setCodigoRespuesta(CodigoRespuesta.ERROR);
				respuesta.setMensaje(Bundle.MSG2_ERROR_TOKEN);
			}
		} else {
			//Password inválido
			respuesta.setCodigoRespuesta(CodigoRespuesta.ERROR);
			respuesta.setMensaje(Bundle.MSG2_ERROR_PASSWORD);
		}
		return respuesta;
	}
	
	public Respuesta<Usuario> guardarAutenticacionFacebook(Usuario usuario, String idFacebook){
		Respuesta<Usuario> respuesta = new Respuesta<>();
		usuario.setIdFacebook(idFacebook);
		usuario.setHasToken(Boolean.TRUE);
		usuarioRepository.save(usuario);
		respuesta.setEntidad(usuario);
		respuesta.setCodigoRespuesta(CodigoRespuesta.OK);
		return respuesta;
	}
}
