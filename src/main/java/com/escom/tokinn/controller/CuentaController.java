package com.escom.tokinn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.services.CuentaService;
import com.escom.tokinn.services.TransaccionService;

@Controller
@RequestMapping("/cuenta")
@SessionAttributes("userData")
public class CuentaController {
	@Autowired
	@Qualifier("cuentaService")
	private CuentaService cuentaService;
	
	@Autowired
	@Qualifier("transaccionService")
	private TransaccionService transaccionService;
	
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(NavigationConstants.CUENTA_ADD);
	}
	
	@GetMapping("/gestion")
	public ModelAndView gestionar() {
		ModelAndView mav = new ModelAndView();
		List<Transaccion> transacciones = transaccionService.findAll();
		Double amount = 0.0;
		for(Transaccion transaccion : transacciones) {
			amount+=transaccion.getAmount();
		}
		mav.setViewName(NavigationConstants.CUENTA_INDEX);
		mav.addObject("amount", amount);
		mav.addObject("transacciones",transacciones);
		return mav;
	}
	
	@GetMapping("/test")
	public ModelAndView test() {
		return new ModelAndView(NavigationConstants.CUENTA_VULNERABILIDAD);
	}
	
	@GetMapping("/vinculacion")
	public ModelAndView vinculacion(ModelMap session) {
		Usuario usuario = (Usuario) session.get("userData");
		cuentaService.notificarRegistro(usuario);
		return new ModelAndView(NavigationConstants.VINCULACION);
	}
	
}
