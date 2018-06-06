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
import com.escom.tokinn.constantes.NavigationConstants;
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

	@GetMapping("/confirmar")
	public ModelAndView confirmar() {
		return new ModelAndView(NavigationConstants.CONFIRMAR_FACE);
	}		
	
	@GetMapping("/index")
	public ModelAndView gestionar() {
		return new ModelAndView(NavigationConstants.USUARIO_INDEX);
	}
	
	@GetMapping("/view")
	public ModelAndView showview() {
		return new ModelAndView(NavigationConstants.USUARIO_VIEW);
	}		
	
	@GetMapping("/registro")
	public ModelAndView registrar(Model model, @RequestParam(name="error", required=false)String error) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("error",error);
		return new ModelAndView(NavigationConstants.USUARIO_ADD);
	}
	
	@PostMapping("registro")
	public String registrar(@ModelAttribute("usuario") UsuarioModel model) {
		String redirect = "/tokinn/landing";
		Usuario entidad = usuarioConverter.modelToEntity(model);
		entidad = usuarioService.registrarUsuario(entidad);
		if(entidad != null) {
			//Registro exitoso
			redirect = NavigationConstants.LOGIN_VIEW+"/tokinn/login?success=true";
		} else {
			//Error
			redirect = NavigationConstants.USUARIO_ADD+"/usuario/registro?error=true";
		}
		return "redirect:"+redirect;
	}
	
	@GetMapping("/editar")
	public ModelAndView editar() {
		return new ModelAndView(NavigationConstants.USUARIO_EDIT);
	}
	
	@GetMapping("/password")
	public ModelAndView password() {
		return new ModelAndView(NavigationConstants.USUARIO_PASSWORD);
	}
}
