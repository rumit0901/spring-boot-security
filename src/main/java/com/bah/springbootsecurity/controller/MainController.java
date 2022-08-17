package com.bah.springbootsecurity.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bah.springbootsecurity.utils.WebUtils;

@Controller
public class MainController {

	@RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is welcome Page");
		return "welcomePage";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {
		
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		
		return "adminPage";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET) 
	public String loginPage(Model model) {
		
		return "loginPage";
	}
	
	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET) 
	public String logoutSuccessfulPage(Model model) {
		
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}
	
	@RequestMapping(value = "userInfo", method = RequestMethod.GET) 
	public String userInfo(Model model, Principal principal) {
		
		String username = principal.getName();
		System.out.println("User name: " + username);
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Collection<GrantedAuthority> authories = loginedUser.getAuthorities();
		model.addAttribute("pagename", authories.size() == 1 ? "User" : "Admin");
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		return "userInfoPage";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		
		if(principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			
			String userInfo = WebUtils.toString(loginedUser);
			
			model.addAttribute("userInfo", userInfo);
			
			String message = "Hi " + principal.getName()
							+ "<br> You do not have permission to access this page!";
			
			model.addAttribute("message", message);
		}
		
		return "403Page";
	}
}
