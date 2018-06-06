package com.escom.tokinn.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.constantes.NumerosConstantes;
import com.escom.tokinn.converter.TransaccionConverter;
import com.escom.tokinn.entity.Cuenta;
import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.TransaccionFormModel;
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
	
	private List<TransaccionModel> a = new ArrayList<>();
	
	@GetMapping("/registro")
	public ModelAndView registrar() {
		return new ModelAndView(NavigationConstants.CUENTA_ADD);
	}	
	
	@GetMapping("/gestion")
	public ModelAndView gestionar(Model model, ModelMap session) {
		Double amount = 0.0;
		Usuario usuario = (Usuario) session.get("userData");
		List<TransaccionModel> transacciones = transaccionService.findAllByIdCuenta(usuario.getCuentas().get(NumerosConstantes.NUMERO_CERO).getIdCuenta());
		for(TransaccionModel transaccion : transacciones) {
			amount+=transaccion.getAmount();
		}
		TransaccionFormModel transaccionFormModel = new TransaccionFormModel();
		transaccionFormModel.setTransacciones(transacciones);
		model.addAttribute("amount", amount);
		model.addAttribute("transaccionFormModel", transaccionFormModel);
		return new ModelAndView(NavigationConstants.CUENTA_INDEX);
	}
	
	@GetMapping("/test")
	public ModelAndView test(Model model, ModelMap session) {
		Usuario usuario = (Usuario) session.get("userData");
		System.out.println("Usuario: "+usuario.getNombre());
		TransaccionFormModel transaccionFormModel = new TransaccionFormModel();
		List<TransaccionModel> transacciones = transaccionService.findAllByIdCuenta(usuario.getCuentas().get(NumerosConstantes.NUMERO_CERO).getIdCuenta());
		transaccionFormModel.setTransacciones(transacciones);
		model.addAttribute("transaccionFormModel", transaccionFormModel);
		return new ModelAndView(NavigationConstants.CUENTA_VULNERABILIDAD);
	}
	
	@PostMapping("/test")
	public ModelAndView edit(@ModelAttribute TransaccionFormModel transaccionFormModel, Model  model) {
		System.out.println("IngreseAlMetodoPost");
		System.out.println("TamanioTransacciones: "+transaccionFormModel.getTransacciones());
		Transaccion transaccion;
		for(TransaccionModel transaccionModel : transaccionFormModel.getTransacciones()) {
			System.out.println("IdTransaccionModel: "+transaccionModel.getIdTransaccion());
			transaccion = new Transaccion();
			transaccion = transaccionConverter.modelToEntity(transaccionModel);
			transaccion = transaccionService.edit(transaccion);
		}
		model.addAttribute("transaccionFormModel", transaccionFormModel);
		return new ModelAndView(NavigationConstants.CUENTA_VULNERABILIDAD);
	}
	
	@GetMapping("/pagos")
	public ModelAndView inicio() {
		return new ModelAndView(NavigationConstants.CUENTA_PAGOS);
	}
}
