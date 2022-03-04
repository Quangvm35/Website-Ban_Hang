package com.poly.SOF3021.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;



import com.poly.SOF3021.model.Product;

import com.poly.SOF3021.repository.ProductRepository;

import java.util.List;

@Controller

public class AdminController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/admin")
	public String homeAdmin(ModelMap model) {
		List<Product> list = productRepository.findAll();
		model.addAttribute("products", list);
		return "/admin/HomeAdmin";
	}

}
