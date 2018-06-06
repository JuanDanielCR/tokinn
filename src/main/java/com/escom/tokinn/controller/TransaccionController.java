package com.escom.tokinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.services.TokenService;
import com.escom.tokinn.services.TransaccionService;
@Controller
@RequestMapping("/transaccion")
public class TransaccionController {

	@Autowired
	@Qualifier("transaccionService")
	private TransaccionService transaccionService;
	
	//----------
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
	@GetMapping("/token")
	public ModelAndView token() {
		System.out.println("t: "+tokenService.generateToken("1024260827627402", NavigationConstants.TOKEN_TRANSACCION));
		return new ModelAndView(NavigationConstants.BASE_VIEW);
	}
	//-----------------
		
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(NavigationConstants.TRANSACCION_ADD);
	}
	
	@GetMapping("/consulta")
	public ModelAndView consultar() {
		return new ModelAndView(NavigationConstants.TRANSACCION_VIEW);
	}
}
