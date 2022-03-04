package com.poly.SOF3021.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.poly.SOF3021.dto.ProductDTO;
import com.poly.SOF3021.model.Product;
import com.poly.SOF3021.repository.ProductRepository;

@Controller
@RequestMapping("admin/product")
public class ProductController {
	
 @Autowired 
 ProductRepository productRepository;
 
 	@GetMapping("")
 	public String show(Model model) {
 		
 		List<Product> list = productRepository.findAll();
		model.addAttribute("pro", list);
 		return "admin/product/productlist";
 	}
 	
 	@GetMapping("/add")
 	public String showadd(Model model) {
 		Product product = new Product();
 		model.addAttribute("products", product);
 		return "admin/product/productadd";
 	}
 	
	@GetMapping("/edit/{id}")
	public String edit( Model model,@PathVariable(name="id") int id) {
        Product product = productRepository.getById(id);
        model.addAttribute("product",product);
        return "admin/product/productEdit";
	}
	
	@GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id , Model model){
			productRepository.deleteById(id);
			model.addAttribute("message", "xóa sản phẩm thành công");
		return "redirect:/admin/product";
}
	
	@PostMapping("/add")
	public String save(@ModelAttribute("product") Product product , Model model ) {
		productRepository.save(product);
		model.addAttribute("message", "thêm thành công");
		return "redirect:/admin/product";
	}
	
	@PostMapping("/edit") 
    public String edit(@ModelAttribute("product") Optional<Product> product) {
    	
    	Optional<Product> product2 = productRepository.findById(product.get().getId());
    	if(product2.isEmpty()) {
    		return "redirect:/edit/"+product.get().getId();
    	}
    	productRepository.save(product.get());
    	return "redirect:/admin/product";
    }

	
	
}
