package com.escom.tokinn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	public List<Usuario> findUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario registrarUsuario(Usuario entidad) {
		entidad.setPassword(tokenService.getHash(entidad.getPassword()));
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
	
	public Usuario findByIdMessenger(String idMessenger) {
		Usuario usuario = usuarioRepository.findByIdMessenger(idMessenger);
		return usuario;
	}
	
	public Respuesta<Usuario> verificarLogin(UsuarioModel model) {
		System.out.println("pass: "+model.getPassword()+" id: "+model.getId());
		Respuesta<Usuario> respuesta = new Respuesta<Usuario>();
		Usuario entidad = usuarioRepository.findByIdUsuarioAndPassword(model.getId(), model.getPassword());
		System.out.println("enitdad: "+entidad);
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
	
	public Boolean isBotHabilitado() {
		return Boolean.TRUE;
	}
	
	public Boolean vincularIdMessenger(String tokenVinculacion, String idMessenger) {
		Boolean isValid = Boolean.FALSE;
		List<Usuario> usuarios = usuarioRepository.findAll();
		for(Usuario usuarioActual : usuarios) {
			if(usuarioActual.getIdFacebook() != null) {
				if(tokenService.getHash(usuarioActual.getIdFacebook()).substring(0, NavigationConstants.TOKEN_LENGTH).toUpperCase()
						.equals(tokenVinculacion)) {
					usuarioActual.setIdMessenger(idMessenger);
					usuarioActual = usuarioRepository.save(usuarioActual);
					isValid = Boolean.TRUE;
					break;
				}
			}
		}
		return isValid;
	}
	
	public Usuario update(Usuario usuario) {
		Usuario usuarioUpdate = usuarioRepository.getOne(usuario.getIdUsuario());
		usuarioUpdate.setIdFacebook(null);
		usuarioUpdate.setIdMessenger(null);
		usuarioUpdate.setHasToken(Boolean.FALSE);
		usuarioUpdate = usuarioRepository.save(usuarioUpdate);
		return usuarioUpdate;
	}
}
