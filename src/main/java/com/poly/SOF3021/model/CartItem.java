package com.poly.SOF3021.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer id;
    private Integer productId;
    private String name;
    private String image;
    private Double price;
    private Integer quantity;
}