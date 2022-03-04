package com.poly.SOF3021.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ordersdetails database table.
 * 
 */
@Entity
@Table(name="ordersdetails")
@NamedQuery(name="Ordersdetail.findAll", query="SELECT o FROM Ordersdetail o")
public class Ordersdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private int orderid;

	private double price;

	private int productid;

	private int quantity;

	public Ordersdetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderid() {
		return this.orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProductid() {
		return this.productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}