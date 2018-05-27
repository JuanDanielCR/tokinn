package com.escom.tokinn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/inicio")
	public ModelAndView inicio() {
		return new ModelAndView(Constantes.LANDING_VIEW);
	}
}