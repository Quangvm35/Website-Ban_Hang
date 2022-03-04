package com.poly.SOF3021.controller.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.SOF3021.model.Account;
import com.poly.SOF3021.model.CartItem;
import com.poly.SOF3021.model.Order;
import com.poly.SOF3021.model.Ordersdetail;
import com.poly.SOF3021.model.Product;
import com.poly.SOF3021.repository.AccountRepository;
import com.poly.SOF3021.repository.OrderRepository;
import com.poly.SOF3021.repository.OrdersDetailRepository;
import com.poly.SOF3021.repository.ProductRepository;
import com.poly.SOF3021.service.ShoppingCartServiceImple;

@Controller
public class ShoppingCartController {
	@Autowired
	ProductRepository Prorepository;
	
	@Autowired
	ShoppingCartServiceImple shoppingCartService;
	
	 @RequestMapping("/site/shopping-cart")
	    public String view(Model model) {
	        Collection<CartItem> cartItems = shoppingCartService.getItems();
	        model.addAttribute("cartItems", cartItems);
	        model.addAttribute("total", shoppingCartService.getAmount());
	        model.addAttribute("NoOfItems", shoppingCartService.getCount());
	        model.addAttribute("cartItem" , new CartItem());
	        return "/site/user/shopping-cart";
	    }

	 @GetMapping("/addCart/{productId}")
	 public String addCart(@PathVariable("productId")Integer productId,
             RedirectAttributes params) {
		 Optional<Product> product = Prorepository.findById(productId);
		 if(product.isPresent()){
	            CartItem cartItem = new CartItem();
	            int ranNum = ThreadLocalRandom.current().nextInt(1,10000);
	            cartItem.setId(ranNum);
	            cartItem.setProductId(product.get().getId());
	            cartItem.setName(product.get().getName());
	            cartItem.setQuantity(1);
	            cartItem.setPrice(product.get().getPrice());
	            cartItem.setImage(product.get().getImage());
	            shoppingCartService.add(cartItem);

	        }else{
	            params.addAttribute("message","product is not exists");
	            return "redirect:/site/shopping-cart";
	        }
		 return "redirect:/site/shopping-cart";		 
	 }
	 
	 @GetMapping("/site/remove/{id}")
	    public String remove(@PathVariable("id") Optional<Integer> id,
	                         RedirectAttributes params){
	        if(id.isPresent()) {
	        	shoppingCartService.remove(id.get());
	        }else{
	            params.addAttribute("message","CartItem is not exists");
	        }
	        return  "redirect:/site/shopping-cart";
	    }
	 
	 @PostMapping("/site/update")
	    public String update(
	                        @RequestParam("id") Optional<Integer> Id,
	                         @RequestParam("price") Optional<Double> unitPrice,
	                         @RequestParam("quantity") Optional<Integer> quantity,
	                         RedirectAttributes params){
	        if(Id.isEmpty()  || unitPrice.isEmpty() || quantity.isEmpty()){
	            params.addAttribute("message", "Error! An error occurred. Please try again later!");
	            return "redirect:/admin/shoppingcart/list";
	        }else{
	        	shoppingCartService.update(Id.get(), quantity.get(), unitPrice.get());
	            return "redirect:/site/shopping-cart";
	        }

	    }
	 	
	 
	 @Autowired
	 AccountRepository accountRepository;
	 @Autowired
	 HttpSession session;
	 @Autowired
	 OrderRepository orderRepository;
	 @Autowired
	 ProductRepository productRepository;
	 
	 @Autowired
	 OrdersDetailRepository ordersDetailRepository;
	 @PostMapping("/site/savecart")
	    public String save(RedirectAttributes params,
	                       Model model,
	                       @RequestParam("address") String address ){
	        Order entity = new Order();
	        
//	        BeanUtils.copyProperties(dto, entity);
	        // User lấy từ session

	        Account user = accountRepository.findById(session.getAttribute("username").toString()).get();
	        entity.setTotal(shoppingCartService.getAmount());
	        entity.setAccount(user);
	        entity.setCreate_date(new Date());
	        entity.setTotal(shoppingCartService.getAmount());
	        entity.setAddress(address);
	        Order od = orderRepository.save(entity);
	        for(int i = 0;i < shoppingCartService.getCount(); i++){
	            Ordersdetail orderdetail = new Ordersdetail();
	            
	            orderdetail.setOrderid(od.getId());
	            Collection<CartItem> listCart= shoppingCartService.getItems();
		        List<CartItem> list = new ArrayList<CartItem>(listCart);
	            Product product = productRepository.getById(list.get(i).getProductId());
		        orderdetail.setQuantity(list.get(i).getQuantity());
		        orderdetail.setPrice(list.get(i).getPrice());
		        orderdetail.setPrice(list.get(i).getPrice());
		        orderdetail.setProductid(product.getId());
		        ordersDetailRepository.save(orderdetail);
	        }
	        shoppingCartService.clear();
	        params.addAttribute("message", "Add success");
	        return "redirect:/product";
	    }
}
