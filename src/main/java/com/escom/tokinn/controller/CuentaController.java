package com.escom.tokinn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.converter.TransaccionConverter;
import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.model.TransaccionModel;
import com.escom.tokinn.model.UsuarioModel;
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
	
	@Autowired
	@Qualifier("transaccionConverter")
	private TransaccionConverter transaccionConverter;
	
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(NavigationConstants.CUENTA_ADD);
	}
	
	@GetMapping("/gestion")
	public ModelAndView gestionar(@ModelAttribute("usuario") UsuarioModel model, ModelMap session) {
		ModelAndView mav = new ModelAndView();
		List<TransaccionModel> transacciones = new ArrayList<>();
		TransaccionModel transaccionModel;
		Double amount = 0.0;
		for(Transaccion transaccion : transaccionService.findTransacciones()) {
			transaccionModel = new TransaccionModel();
			transaccionModel = transaccionConverter.entityToModel(transaccion);
			transacciones.add(transaccionModel);
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
	
	@GetMapping("/pagos")
	public ModelAndView inicio() {
		return new ModelAndView(NavigationConstants.CUENTA_PAGOS);
	}
}
