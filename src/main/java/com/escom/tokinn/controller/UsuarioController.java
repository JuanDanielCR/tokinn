package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.Constantes;
import com.escom.tokinn.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(Constantes.USUARIO_ADD);
	}
	
	@GetMapping("/perfil")
	public ModelAndView editar() {
		return new ModelAndView(Constantes.USUARIO_EDIT);
	}
	
	@GetMapping("/password")
	public ModelAndView password() {
		return new ModelAndView(Constantes.USUARIO_PASSWORD);
	}
}
