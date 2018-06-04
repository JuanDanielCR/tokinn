package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.Constantes;
import com.escom.tokinn.services.CuentaService;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {
	@Autowired
	@Qualifier("cuentaService")
	private CuentaService cuentaService;
	
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(Constantes.CUENTA_ADD);
	}
	
	@GetMapping("/gestion")
	public ModelAndView gestionar() {
		return new ModelAndView(Constantes.CUENTA_INDEX);
	}

	@GetMapping("/pagos")
	public ModelAndView pagos() {
		return new ModelAndView(Constantes.CUENTA_PAGOS);
	}	
	
	@GetMapping("/test")
	public ModelAndView test() {
		return new ModelAndView(Constantes.CUENTA_VULNERABILIDAD);
	}
}
