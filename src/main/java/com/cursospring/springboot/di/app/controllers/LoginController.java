package com.cursospring.springboot.di.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	
	@GetMapping("/login")
	public String login(@RequestParam (value="logout", required =false) String logout,
			@RequestParam (value="error", required =false) String error,
			Model model, Principal principal, 
			RedirectAttributes flash) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya ha iniciado session anteriormente");
			return "redirect:/";
		}
		
		if(logout != null) {
			model.addAttribute("success", "ha cerrado sesion con exito");
		} 
		
		if(error != null) {
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecto");
		}
		
		
		return "login";
	}

}
