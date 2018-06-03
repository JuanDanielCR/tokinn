package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.services.TransaccionService;
@Controller
@RequestMapping("/transaccion")
public class TransaccionController {

	@Autowired
	@Qualifier("transaccionService")
	private TransaccionService transaccionService;
	
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(NavigationConstants.TRANSACCION_ADD);
	}
	
	@GetMapping("/consulta")
	public ModelAndView consultar() {
		return new ModelAndView(NavigationConstants.TRANSACCION_VIEW);
	}
}
