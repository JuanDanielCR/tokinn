package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.escom.tokinn.component.Response;
import com.escom.tokinn.constantes.CodigoRespuesta;
import com.escom.tokinn.constantes.Respuesta;
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.FacebookModel;
import com.escom.tokinn.model.LoginModel;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.UsuarioService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@SessionAttributes("userData")
public class RestController {
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;

	@PostMapping(value = "/login")
	public Response postCustomer(@RequestBody LoginModel model) {
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
	
	@PostMapping("/fb_data")
	public Response postFacebookLogin(@RequestBody FacebookModel model, ModelMap session) {
		Response response;
		Usuario usuario = (Usuario) session.get("userData");
		System.out.println("ID antes: "+usuario.getIdFacebook());
		Respuesta<Usuario> respuesta = usuarioService.guardarAutenticacionFacebook(usuario, model.getIdFacebook());
		if(respuesta.getCodigoRespuesta() == CodigoRespuesta.OK) {
			UsuarioModel usuarioModel = usuarioConverter.entityToModel(respuesta.getEntidad());
			response = new Response("Done", usuarioModel);
			System.out.println("ID despues: "+respuesta.getEntidad().getIdFacebook());
		}else {
			response = new Response("Error", new UsuarioModel());
		}
		//Usuario sessionUser = (Usuario) session.get("userData");
		return response;
	}
}