package com.poly.SOF3021.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.SOF3021.model.Product;
import com.poly.SOF3021.repository.ProductRepository;

@Controller
public class homeController {
	@Autowired 
	PasswordEncoder passwordEncoder;
	@Autowired
	ProductRepository productRepository;
	@GetMapping({"","/fpolyshop.vn"})
	public String index() {
		System.out.println(passwordEncoder.encode("1"));
		return "/site/index";
	}
	@GetMapping("/product")
	public String Product(Model model) {
		//truy van va sap xep
        Pageable pageable = PageRequest.of(0, 5, Sort.by((Sort.Direction.ASC), "id"));
        Page<Product> resultPage = productRepository.findAll(pageable);
        if (resultPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, resultPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("productsPage", resultPage);
//        if (message.length() > 0) {
//            model.addAttribute("message", message);
//        }
		return "/site/user/PrList";
	}
	
	boolean sortIndex = true;
	@GetMapping("/product/sort")
    public String sortAccount(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam("sortby") Optional<String> sortType,
                              @RequestParam("sort") Optional<Boolean> sort,
                              Model model,
                              @ModelAttribute("message") String message) {
        int curentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String sortName = sortType.orElse("id");
        Page<Product> resultPage = null;
        //truy van va sap xep
        Pageable pageable = PageRequest.of(curentPage - 1, pageSize, Sort.by((sortIndex ? Sort.Direction.DESC : Sort.Direction.ASC), sortName));
        resultPage = productRepository.findAll(pageable);
        boolean sortPage = sort.orElse(false);
        if (sortPage) sortIndex = !sortIndex;
        if (resultPage.getTotalPages() > 0) {
            //gender số trang ra các trang nhỏ
            List<Integer> pageNumbers = IntStream.rangeClosed(1, resultPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("productsPage", resultPage);
        if (message.length() > 0) {
            model.addAttribute("message", message);
        }
        return "/site/user/PrList";
    }
	
//	@GetMapping("/list-order")
	

}
