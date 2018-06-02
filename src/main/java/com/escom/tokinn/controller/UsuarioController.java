package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.Constantes;
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;
	
	@GetMapping("/index")
	public ModelAndView gestionar() {
		return new ModelAndView(Constantes.USUARIO_INDEX);
	}
	
	@GetMapping("/registro")
	public ModelAndView registrar(Model model, @RequestParam(name="error", required=false)String error) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("error",error);
		return new ModelAndView(Constantes.USUARIO_ADD);
	}
	
	@PostMapping("registro")
	public String registrar(@ModelAttribute("usuario") UsuarioModel model) {
		String redirect = Constantes.LANDING_VIEW;
		Usuario entidad = usuarioConverter.modelToEntity(model);
		entidad = usuarioService.registrarUsuario(entidad);
		if(entidad != null) {
			//Registro exitoso
			redirect = Constantes.LOGIN_VIEW+"?success=true";
		} else {
			//Error
			redirect = Constantes.USUARIO_ADD+"?error=true";
		}
		return "redirect:"+redirect;
	}
	
	@GetMapping("/editar")
	public ModelAndView editar() {
		return new ModelAndView(Constantes.USUARIO_EDIT);
	}
	
	@GetMapping("/password")
	public ModelAndView password() {
		return new ModelAndView(Constantes.USUARIO_PASSWORD);
	}
}
