package com.poly.SOF3021.controller.admin;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.poly.SOF3021.dto.CategoryDTO;
import com.poly.SOF3021.model.Category;

import com.poly.SOF3021.repository.CategoryRepository;





@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryRepository.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/CategoryList";
	}
	
	@GetMapping("/search")
	public String search() {
		return "admin/categories/search";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
	        Category category = new Category();
	        model.addAttribute("category", category);
		return "admin/categories/AddCategory";
	}
	
	@GetMapping("/edit/{id}")
	public String edit( Model model , @PathVariable("id") int id) {
		Category category = categoryRepository.getById(id);
		model.addAttribute("category", category);
        return "admin/categories/EditCategory";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable( "id") int id , Model model) {
		categoryRepository.deleteById(id);
		model.addAttribute("message", "xóa thành công");
		return"redirect:/admin/categories";
	}
	
	@PostMapping("/Edit")
	public String EditCategory(@ModelAttribute("product") Optional<Category> category) {
		Optional<Category> category2 = categoryRepository.findById(category.get().getId());
		if(category2.isEmpty()){
			return "redirect:/edit/"+category.get().getId();
		}
    	categoryRepository.save(category.get());
		return "redirect:/admin/categories";
	}
	 
	@PostMapping("/add")
	public String addCategories(@ModelAttribute("category") Category category , Model model) {
		categoryRepository.save(category);
		model.addAttribute("message", "them thanh cong");
		return "redirect:/admin/categories";
	}
	
}
