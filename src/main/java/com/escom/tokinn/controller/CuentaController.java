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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.constantes.NumerosConstantes;
import com.escom.tokinn.converter.CuentaConverter;
import com.escom.tokinn.converter.TransaccionConverter;
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.CuentaModel;
import com.escom.tokinn.model.EstadoCuentaModel;
import com.escom.tokinn.model.TransaccionFormModel;
import com.escom.tokinn.model.TransaccionModel;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.CuentaService;
import com.escom.tokinn.services.EstadoCuentaService;
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
	@Qualifier("estadoCuentaService")
	private EstadoCuentaService estadoCuentaService;
	
	@Autowired
	@Qualifier("transaccionConverter")
	private TransaccionConverter transaccionConverter;
	
	@Autowired
	@Qualifier("cuentaConverter")
	private CuentaConverter cuentaConverter;
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;
	
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
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		model.addAttribute("usuarioModel", usuarioModel);
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
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		model.addAttribute("usuarioModel", usuarioModel);
		model.addAttribute("transaccionFormModel", transaccionFormModel);
		return new ModelAndView(NavigationConstants.CUENTA_VULNERABILIDAD);
	}
	
	@PostMapping("/test")
	public ModelAndView edit(@ModelAttribute TransaccionFormModel transaccionFormModel, Model  model, ModelMap session) {
		Transaccion transaccion;
		for(TransaccionModel transaccionModel : transaccionFormModel.getTransacciones()) {
			transaccion = new Transaccion();
			transaccion = transaccionConverter.modelToEntity(transaccionModel);
			transaccion = transaccionService.edit(transaccion);
		}
		model.addAttribute("transaccionFormModel", transaccionFormModel);
		Usuario usuario = (Usuario) session.get("userData");
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		model.addAttribute("usuarioModel", usuarioModel);
		return new ModelAndView(NavigationConstants.CUENTA_VULNERABILIDAD);
	}
	
	@GetMapping("/pagos")
	public ModelAndView inicio(Model model, ModelMap session,
			@RequestParam(name="token", required=false) String token) {
		Usuario usuario = (Usuario) session.get("userData");
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		model.addAttribute("usuarioModel", usuarioModel);
		model.addAttribute("transaccion", new TransaccionModel());
		model.addAttribute("cuenta", new CuentaModel());
		model.addAttribute("token", token);
		return new ModelAndView(NavigationConstants.CUENTA_PAGOS);
	}
	
	@PostMapping("/pagos")
	public String login(@ModelAttribute("transaccion") TransaccionModel modelTransaccion,
						@ModelAttribute("cuenta") CuentaModel modelCuenta, 
						ModelMap session) {
		String redirect = "/usuario/index";
		Usuario usuario = (Usuario) session.get("userData");
		Transaccion respuesta = transaccionService.add(transaccionConverter.modelToEntity(modelTransaccion), 
				usuario, cuentaConverter.modelToEntity(modelCuenta));
		if(respuesta.getIdTransaccion()==null){
			redirect = "/cuenta/pagos?token=true";
		}	
		return "redirect:"+redirect;
	}
	
	@GetMapping("/vinculacion")
	public ModelAndView vinculacion(Model model, ModelMap session) {
		Usuario usuario = (Usuario) session.get("userData");
		cuentaService.notificarRegistro(usuario);
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		model.addAttribute("usuarioModel", usuarioModel);
		System.out.println("----> Vinculacion terminada");
		return new ModelAndView(NavigationConstants.USUARIO_INDEX);
	}
	
	@GetMapping("/crear")
	public ModelAndView crear(Model model, ModelMap session) {
		Usuario usuario = (Usuario) session.get("userData");
		List<EstadoCuentaModel> estadoCuentaModelList = estadoCuentaService.findByIdCuenta(usuario.getCuentas().get(NumerosConstantes.NUMERO_CERO).getIdCuenta());
		model.addAttribute("estadoCuentaModelList", estadoCuentaModelList);
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		model.addAttribute("usuarioModel", usuarioModel);
		return new ModelAndView(NavigationConstants.CUENTA_CREAR);
	}	
}
