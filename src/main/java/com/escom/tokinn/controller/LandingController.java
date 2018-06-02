package com.escom.tokinn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.Constantes;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.services.UsuarioService;

@Controller
@RequestMapping("/tokinn")
public class LandingController {
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@GetMapping("/prueba")
	public ModelAndView prueba() {
		List<Usuario> listaUsuarios = usuarioService.findUsuarios();
		System.out.println("listaUsuarios: "+listaUsuarios.isEmpty());
		return new ModelAndView(Constantes.LANDING_VIEW);
	}
	
	@GetMapping("/landing")
	public ModelAndView inicio() {
		return new ModelAndView(Constantes.LANDING_VIEW);
	}
	
	@GetMapping("/afores")
	public ModelAndView afore() {
		return new ModelAndView(Constantes.AFORES_VIEW);
	}
	
	@GetMapping("/inversiones")
	public ModelAndView inversiones() {
		return new ModelAndView(Constantes.INVERSIONES_VIEW);
	}
	
	@GetMapping("/login")
	public ModelAndView login(Model model, 
			@RequestParam(name="error", required=false) String error, 
			@RequestParam(name="logout", required=false) String logout,
			@RequestParam(name="success", required=false) String success) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("logout", logout);
		return new ModelAndView(Constantes.LOGIN_VIEW);
	}
	
}