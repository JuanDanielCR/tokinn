package com.escom.tokinn.controller;

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
import com.escom.tokinn.converter.UsuarioConverter;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.UsuarioModel;
import com.escom.tokinn.services.TokenService;
import com.escom.tokinn.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("userData")
public class UsuarioController {
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;

	@GetMapping("/confirmar")
	public ModelAndView confirmar() {
		return new ModelAndView(NavigationConstants.CONFIRMAR_FACE);
	}		

	@GetMapping("/desvincular")
	public ModelAndView desconfirmar() {
		return new ModelAndView(NavigationConstants.DESVINCULAR_TOKEN);
	}			
	
	//----------
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
	
	@GetMapping("/token")
	public ModelAndView token() {
		System.out.println("t: "+tokenService.generateToken("1024260827627402", NavigationConstants.TOKEN_INICIO));
		return new ModelAndView(NavigationConstants.BASE_VIEW);
	}
	//-----------------
	
	@GetMapping("/index")
	public ModelAndView gestionar(Model model, ModelMap session) {
		Usuario usuario = (Usuario) session.get("userData");
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		usuarioModel.setCuenta(usuario.getCuentas().get(NumerosConstantes.NUMERO_CERO));
		model.addAttribute("usuarioModel", usuarioModel);
		return new ModelAndView(NavigationConstants.USUARIO_INDEX);
	}
	
	@GetMapping("/view")
	public ModelAndView showview(Model model, ModelMap session) {
		Usuario usuario = (Usuario) session.get("userData");
		UsuarioModel usuarioModel = usuarioConverter.entityToModel(usuario);
		usuarioModel.setCuenta(usuario.getCuentas().get(NumerosConstantes.NUMERO_CERO));
		model.addAttribute("usuarioModel", usuarioModel);
		return new ModelAndView(NavigationConstants.USUARIO_VIEW);
	}		
	
	@GetMapping("/registro")
	public ModelAndView registrar(Model model, @RequestParam(name="error", required=false)String error) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("error",error);
		return new ModelAndView(NavigationConstants.USUARIO_ADD);
	}
	
	@PostMapping("registro")
	public String registrar(@ModelAttribute("usuario") UsuarioModel model) {
		String redirect = "/tokinn/landing";
		Usuario entidad = usuarioConverter.modelToEntity(model);
		entidad = usuarioService.registrarUsuario(entidad);
		if(entidad != null) {
			//Registro exitoso
			redirect = "/tokinn/login?success=true";
		} else {
			//Error
			redirect = "/usuario/registro?error=true";
		}
		return "redirect:"+redirect;
	}
	
	@GetMapping("/editar")
	public ModelAndView editar() {
		return new ModelAndView(NavigationConstants.USUARIO_EDIT);
	}
	
	@GetMapping("/password")
	public ModelAndView password() {
		return new ModelAndView(NavigationConstants.USUARIO_PASSWORD);
	}
}
