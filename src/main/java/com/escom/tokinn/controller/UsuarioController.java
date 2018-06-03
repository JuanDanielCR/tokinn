package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("userData")
public class UsuarioController {
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;
	
	@GetMapping("/index")
	public ModelAndView gestionar(Model model, ModelMap session,
			@RequestParam(name="error", required=false) String error,
			@RequestParam(name="success", required=false) String success) {
		ModelAndView mov= new ModelAndView(NavigationConstants.USUARIO_INDEX);
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		Usuario usuario = (Usuario) session.get("userData");
		model.addAttribute("usuario",usuario);
		return mov;
	}
	
	@GetMapping("/registro")
	public ModelAndView registrar(Model model, @RequestParam(name="error", required=false)String error) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("error",error);
		return new ModelAndView(NavigationConstants.USUARIO_ADD);
	}
	
	@PostMapping("registro")
	public String registrar(@ModelAttribute("usuario") UsuarioModel model) {
		String redirect = NavigationConstants.LANDING_VIEW;
		Usuario entidad = usuarioConverter.modelToEntity(model);
		entidad = usuarioService.registrarUsuario(entidad);
		if(entidad != null) {
			//Registro exitoso
			redirect = NavigationConstants.LOGIN_VIEW+"?success=true";
		} else {
			//Error
			redirect = NavigationConstants.USUARIO_ADD+"?error=true";
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
