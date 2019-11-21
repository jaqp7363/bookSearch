package com.jaqp7363.book.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("MainController")
public class MainController {
	@RequestMapping("/")
	public ModelAndView mainHome(Authentication auth) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("main");
		return model;
	}
	
	@RequestMapping("/singUpPopup")
	public void singUpPopup(Model model) throws IOException {
	}
	
	@RequestMapping("/bookDetail")
	public void bookDetail(Model model, @RequestParam Map<String, String> map) throws IOException {
		model.addAttribute("title", map.get("title"));
		model.addAttribute("thumbnail", map.get("thumbnail"));
		model.addAttribute("contents", map.get("contents"));
		model.addAttribute("isbn", map.get("isbn"));
		model.addAttribute("authors", map.get("authors"));
		model.addAttribute("publisher", map.get("publisher"));
		model.addAttribute("datetime", map.get("datetime"));
		model.addAttribute("price", map.get("price"));
		model.addAttribute("sale_price", map.get("sale_price"));
	}
	
	@RequestMapping("/mySearchHist")
	public void mySearchHist(Model model) throws IOException {
	}
}
