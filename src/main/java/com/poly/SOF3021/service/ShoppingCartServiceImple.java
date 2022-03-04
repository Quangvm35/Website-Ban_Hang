package com.poly.SOF3021.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.SOF3021.model.CartItem;

@Service
@SessionScope
public class ShoppingCartServiceImple {
	 private Map<Integer, CartItem> map = new HashMap<>();
	 public void add(CartItem cartItem){
	        CartItem item = map.get(cartItem.getId());
	        
	        Collection<CartItem> listCart=  map.values();
	        List<CartItem> list = new ArrayList<CartItem>(listCart);
	        CartItem exCartItem = null;
	       if(map.size() <= 0){
	           map.put(cartItem.getId(), cartItem);
	       }else{
	           if(item == null){
	               for(int i = 0; i < map.size(); i++){
	                   if(cartItem.getPrice().longValue() == list.get(i).getPrice().longValue()){
	                       exCartItem = list.get(i);
	                       break;
	                   }
	               }
	               if(exCartItem != null){
	                   map.get(exCartItem.getId()).setQuantity(cartItem.getQuantity() + exCartItem.getQuantity());
	               }else{
	                   map.put(cartItem.getId(), cartItem);
	               }

	           }else {
	               int ranNum = ThreadLocalRandom.current().nextInt(1, 10000);
	               Set<Integer> set = map.keySet();
	               if (map.size() > 0) {
	                   for (int i = 0; i < set.size(); i++) {
	                       if (ranNum == map.get(set).getId()) {
	                           ranNum = ThreadLocalRandom.current().nextInt(1, 10000);
	                       }
	                   }
	               }
	               map.put(ranNum, cartItem);
	           }
	       }
	    }
	 
	 public void update(Integer id, Integer quantity, Double unitPrice){
	        CartItem item = map.get(id);
	            map.get(item.getId()).setQuantity(quantity);
	            map.get(item.getId()).setPrice(unitPrice);
	            if(item.getQuantity() <= 0){
	                map.remove(id);
	            }else if(item.getPrice() < 0){
	                map.get(id).setPrice(0.0);
	            }
	        CartItem item1 = map.get(id);
	            if(item1 != null){
	            	 Collection<CartItem> listCart=  map.values();
	     	        List<CartItem> list = new ArrayList<CartItem>(listCart);
	                CartItem item_check = null;
	                for(int i = 0; i < map.size(); i++){
	                    if(     id.intValue() != list.get(i).getId().intValue()	                           
	                            && item.getPrice().doubleValue() == list.get(i).getPrice().doubleValue()
	                    ){
	                        item_check = list.get(i);
	                        break;
	                    }
	                }
	                if(item_check != null){
	                    map.get(id).setQuantity(item1.getQuantity() + item_check.getQuantity());
	                    map.remove(item_check.getId());
	                }
	            }

	            if(item.getQuantity() <= 0){
	                map.remove(id);
	            }else if(item.getPrice() < 0){
	                map.get(id).setPrice(0.0);
	            }
	    }
	 
	 public void remove(Integer id){
	        map.remove(id);
	    }
	 
	 
	 public Collection<CartItem> getItems(){
	        return  map.values();
	    }

	    public int getCount(){
	        if(map.isEmpty()){
	            return 0;
	        }
	        return map.values().size();
	    }


	    public double getAmount(){
	        return map.values().stream().mapToDouble(
	                item -> item.getQuantity() * item.getPrice()
	        ).sum();
	    }
	    public void clear() {
	        map.clear();
	    }
}
