package com.escom.tokinn.converter;

import org.springframework.stereotype.Component;

import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.UsuarioModel;

@Component("usuarioConverter")
public class UsuarioConverter {
	//Model -> Entity
	public Usuario modelToEntity(UsuarioModel model) {
		Usuario entidad = new Usuario();
		entidad.setIdUsuario(model.getId());
		entidad.setIdFacebook(model.getIdFacebook());
		entidad.setPassword(model.getPassword());
		entidad.setNombre(model.getNombre());
		entidad.setApellidoPaterno(model.getApellidoPaterno());
		entidad.setApellidoMaterno(model.getApellidoMaterno());
		entidad.setEmail(model.getEmail());
		entidad.setDireccion(model.getDireccion());
		entidad.setTelefono(model.getTelefono());
		entidad.setHasToken(model.getHasToken());
		return entidad;
	}
	//Entity -> Model
	public UsuarioModel entityToModel(Usuario entidad) {
		UsuarioModel model = new UsuarioModel();
		model.setId(entidad.getIdUsuario());
		model.setIdFacebook(entidad.getIdFacebook());
		model.setPassword(entidad.getPassword());
		model.setNombre(entidad.getNombre());
		model.setApellidoPaterno(entidad.getApellidoPaterno());
		model.setApellidoMaterno(entidad.getApellidoMaterno());
		model.setEmail(entidad.getEmail());
		model.setDireccion(entidad.getDireccion());
		model.setTelefono(entidad.getTelefono());
		model.setHasToken(entidad.getHasToken());
		return model;
	}
}
