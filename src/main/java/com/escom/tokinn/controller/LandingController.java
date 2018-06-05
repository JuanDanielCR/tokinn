package com.escom.tokinn.controller;

import java.util.List;

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

import com.escom.tokinn.constantes.CodigoRespuesta;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.constantes.Respuesta;
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.UsuarioService;

@Controller
@RequestMapping("/tokinn")
@SessionAttributes("userData")
public class LandingController {
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;
	
	public Usuario userData;
	
	@GetMapping("/prueba")
	public ModelAndView prueba() {
		List<Usuario> listaUsuarios = usuarioService.findUsuarios();
		System.out.println("listaUsuarios: "+listaUsuarios.isEmpty());
		return new ModelAndView(NavigationConstants.LANDING_VIEW);
	}
	
	@GetMapping("/landing")
	public ModelAndView inicio() {
		return new ModelAndView(NavigationConstants.LANDING_VIEW);
	}
	
	@GetMapping("/afores")
	public ModelAndView afore() {
		return new ModelAndView(NavigationConstants.AFORES_VIEW);
	}
	
	@GetMapping("/inversiones")
	public ModelAndView inversiones() {
		return new ModelAndView(NavigationConstants.INVERSIONES_VIEW);
	}
	
	@GetMapping("/login")
	public ModelAndView login(Model model, 
			@RequestParam(name="error", required=false) String error, 
			@RequestParam(name="logout", required=false) String logout,
			@RequestParam(name="success", required=false) String success) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("logout", logout);
		return new ModelAndView(NavigationConstants.LOGIN_VIEW);
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("usuario") UsuarioModel model, ModelMap session) {
		String redirect = NavigationConstants.LOGIN_VIEW;
		System.out.println("model: "+model.getId());
		Respuesta<Usuario> respuestaLogin = usuarioService.verificarLogin(model);
		if(respuestaLogin.getCodigoRespuesta().equals(CodigoRespuesta.OK)) {
			//Registro exitoso - Subir a session
			userData = respuestaLogin.getEntidad();
			session.put("userData", userData);
			redirect = NavigationConstants.USUARIO_INDEX+"?success=true";
		} else {
			//Error
			redirect = NavigationConstants.LOGIN_VIEW+"?error=true";
		}
		return "redirect:"+redirect;
	}
	
}