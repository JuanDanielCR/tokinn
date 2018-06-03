package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.escom.tokinn.component.Response;
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.LoginModel;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.UsuarioService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;

	@PostMapping(value = "/login")
	public Response postCustomer(@RequestBody LoginModel model) {
		// Create Response Object
		Response response;
		Usuario entidad = usuarioService.findUsuarioByCuenta(model.getNumeroTarjeta(), model.getCvv());
		if(entidad.getIdUsuario() != null) {
			UsuarioModel usuarioModel = usuarioConverter.entityToModel(entidad);
			response = new Response("Done", usuarioModel);
		}else {
			response = new Response("Error", new UsuarioModel());
		}
		 
		return response;
	}
}