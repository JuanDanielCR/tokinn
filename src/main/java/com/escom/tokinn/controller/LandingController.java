package com.escom.tokinn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escom.tokinn.constantes.Constantes;

@Controller
@RequestMapping("/tokinn")
public class LandingController {
	
	@GetMapping("/inicio")
	public ModelAndView inicio() {
		return new ModelAndView(Constantes.LANDING_VIEW);
	}
}